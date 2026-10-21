package trigger;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import area.Area;
import behaviour.GEntity;
import manager.IEnterMap;
import manager.ILeaveMap;

import java.util.*;

/**
 * Created by zyao on 2025/1/15 16:42 <p>
 *
 * 挂载在GEntity上的区域触发器, 做为组件存在
 * 目前只支持一个Trigger, 支持多个trigger有点复杂了, 感觉没必要<p>
 * CommonTriggerManager:<p>
 * 1. 当对象进入触发器的视野后, 触发器开始监听进入视野对象的移动<p>
 * 2. 当对象移动时, 会将被监听对象加入到needCalEntities中, 然后每隔一定的帧数计算一次, 计算的同时把对象<p>
 * 从needCalEntities中移除, 如果此次计算对象在Area中, 而不在inAreaEntities中,
 * 则触发listener的triggerIn, 如果此次计算对象不在Area中, 而在inAreaEntities中,
 * 则触发listener的triggerOut, 同时如果对象在Area中, 且不在inAreaEntities中,
 * 将对象加入到inAreaEntities中, 同时如果对象不在Area中, 则将对象从inAreaEntities中移除
 * 3. 当对象离开触发器的视野后, 触发器停止监听进入视野对象的移动, 同时将观察对象从moveListeners中移除,<p>
 * 4. listeners是由应用层添加的, 需要由应用层管理生命期<p>
 * 5. 触发器构建的时候, 视野中的Entity就要开始被监听移动, 同时如果判断在Area中, 要将对象加入到inAreaEntities中<p>
 * 优化实现, GridTriggerManager, 仅支持基于格子的视野管理:
 * 1. 触发器构建的时候, 确认触发器占的视野格子信息(两部分, 半占用, 全占用), 视野中的Entity分成两部分, 一部分在格子外,
 * 一部分在触发器所占的格子内, 格子外的对象, 只监听跨格事件, 在格子内的对象, 监听跨格和移动事件, 格子外的对象
 * 和格子内的对象, 会因为跨格的不同, 相互转化, 同时管理监听移动事件<p>
 * 2. 对象移动之后的处理和老版相同, 但判断是否在Area中, 对于全占用的视野格子, 可以直接判断在Area中, 对于半占用的视野格子,
 * 才进行Area的判断<p>
 * 3. 当对象离开触发器的视野后, 触发器停止监听进入视野对象的移动, 同时将观察对象从各种容器中移除,<p>
 * 4. listeners和老版一样<p>
 * 5. 触发器创建时, 就要执行一次视野内对象的计算, 和老版逻辑一样<p>
 * 优化补充:
 * 1. 优化触发器只能用于使用格子AOI地图, 所以触发器不提供公开的构造方法
 *
 *
 */
public abstract class TriggerManager implements IEnterMap, ILeaveMap {
    private static final Logger logger = LoggerFactory.getLogger(TriggerManager.class);
    public static final int TICK_INTERVAL_MS = 300;
    protected final TriggerHost host;
    protected boolean active;
    protected long lifecycle;
    protected final GEntity triggerEntity;
    protected final Area area;
    private final Set<Integer> caredObjTypes = new HashSet<>();
    public List<ITriggerListener> listeners = new ArrayList<>();
    private long lastTickMills = 0;

    protected TriggerManager(TriggerHost host, GEntity triggerEntity, Area area, List<Integer> caredObjTypes) {
        this.host = host;
        this.triggerEntity = triggerEntity;
        this.area = area;
        this.caredObjTypes.addAll(caredObjTypes);
    }

    public static TriggerManager create(TriggerHost host, GEntity triggerEntity, Area area, List<Integer> caredObjTypes) {
        if (host.getGridMapAoiManager() != null && (area instanceof area.AreaCircle ||
                area instanceof area.AreaRectangle rectangle && !rectangle.isRotation())) {
            return new GridTriggerManager(host, triggerEntity, area, caredObjTypes);
        }
        return new CommonTriggerManager(host, triggerEntity, area, caredObjTypes);
    }

    protected final List<GEntity> seeingEntities() {
        List<GEntity> result = new ArrayList<>();
        for (GEntity entity : host.getSeeingEntities(triggerEntity)) { result.add(entity); }
        return result;
    }

    protected static Logger getLogger() {
        return logger;
    }
    protected final boolean notCaredObjType(GEntity observedEntity) {
        return !caredObjTypes.contains(host.getEntityType(observedEntity));
    }

    protected abstract void onTriggerEnterMap();

    protected abstract void onTriggerLeaveMap();

    protected abstract void onTick(long deltaMills);

    @Override
    public void onEnterMap() {
        if (active) { return; }
        active = true;
        lifecycle++;
        lastTickMills = host.getNowMills();
        // 对视野内的对象先执行一次计算
        onTriggerEnterMap();
    }

    @Override
    public void onLeaveMap() {
        if (!active) { return; }
        active = false;
        lifecycle++;
        onTriggerLeaveMap();
    }

    public void tick(long deltaMills) {
        if (!active) { return; }
        long nowMs = host.getNowMills();
        if (nowMs - lastTickMills < TICK_INTERVAL_MS) {
            return;
        }
        lastTickMills = nowMs;
        onTick(deltaMills);
    }

    protected final void triggerIn(GEntity entity) {
        logger.debug("triggerIn trigger:{} inEntity:{}, entityType:{}", triggerEntity.getInstanceId(), entity.getInstanceId(), host.getEntityType(entity));
        for (var listener : List.copyOf(listeners)) {
            listener.triggerIn(entity);
        }
    }

    protected final void triggerOut(GEntity entity) {
        logger.debug("triggerOut trigger:{} outEntity:{}, entityType:{}", triggerEntity.getInstanceId(), entity.getInstanceId(), host.getEntityType(entity));
        for (var listener : List.copyOf(listeners)) {
            listener.triggerOut(entity);
        }
    }
    public void addTriggerListener(ITriggerListener listener) {
        listeners.add(listener);
    }

    public void removeTriggerListener(ITriggerListener listener) {
        listeners.remove(listener);
    }
}
