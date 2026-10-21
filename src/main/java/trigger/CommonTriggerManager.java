package trigger;

import area.Area;
import behaviour.GEntity;
import event.IMapObjectEventListener;
import event.object.EventEnterAoi;
import event.object.EventLeaveAoi;
import event.object.EventMove;
import event.object.EventObjectLeaveMap;

import java.util.*;

/**
 * Created by zyao on 2025/2/8 10:33
 */
public class CommonTriggerManager extends TriggerManager {
    private IMapObjectEventListener<EventLeaveAoi> leaveAoiListener;
    private IMapObjectEventListener<EventEnterAoi> enterAoiListener;
    private final Map<GEntity, IMapObjectEventListener<EventMove>> moveListeners = new HashMap<>();
    private final Map<GEntity, IMapObjectEventListener<EventObjectLeaveMap>> leaveMapListeners = new HashMap<>();
    private final Set<GEntity> inAreaEntities = new HashSet<>();
    private final Set<GEntity> needCalEntities = new HashSet<>();
    CommonTriggerManager(TriggerHost host, GEntity triggerEntity, Area area, List<Integer> caredObjTypes) {
        super(host, triggerEntity, area, caredObjTypes);
    }

    // 触发器开始监听进入视野对象的移动
    private void initMoveListener(GEntity observedEntity) {
        if (moveListeners.containsKey(observedEntity)) { return; }
        getLogger().debug("initMoveListener trigger:{} moveEntity:{}, moveEntityType:{}", triggerEntity.getInstanceId(), observedEntity.getInstanceId(), host.getEntityType(observedEntity));
        IMapObjectEventListener<EventMove> listener = event -> {
            // 标脏
            needCalEntities.add(observedEntity);
        };

        observedEntity.registerListener(EventMove.class, listener);
        moveListeners.put(observedEntity, listener);
        IMapObjectEventListener<EventObjectLeaveMap> leave = event -> removeEntity(observedEntity);
        observedEntity.registerListener(EventObjectLeaveMap.class, leave);
        leaveMapListeners.put(observedEntity, leave);
    }
    @Override
    public void onTriggerEnterMap() {
        // 对视野内的对象先执行一次计算
        var observerList = seeingEntities();
        for (GEntity observer : observerList) {
            if (notCaredObjType(observer)) {
                continue;
            }
            initMoveListener(observer);
            if (area.isInArea(observer.getPos())) {
                inAreaEntities.add(observer);
            }
        }

        // 监听自己的进出视野事件
        leaveAoiListener = event -> {
            if (notCaredObjType(event.leaveEntity)) {
                return;
            }
            getLogger().debug("leaveAoiListener trigger:{} leaveAoiEntity:{}, leaveEntityType:{}", triggerEntity.getInstanceId(),event.leaveEntity.getInstanceId(), host.getEntityType(event.leaveEntity));

            removeEntity(event.leaveEntity);
        };
        this.triggerEntity.registerListener(EventLeaveAoi.class, leaveAoiListener);

        enterAoiListener = event -> {
            if (notCaredObjType(event.enterEntity)) {
                return;
            }
            getLogger().debug("enterAoiListener trigger:{} enterAoiEntity:{}, enterEntityType:{}", triggerEntity.getInstanceId(),event.enterEntity.getInstanceId(), host.getEntityType(event.enterEntity));
            initMoveListener(event.enterEntity);
            needCalEntities.add(event.enterEntity);
        };
        this.triggerEntity.registerListener(EventEnterAoi.class, enterAoiListener);
    }

    private void removeEntity(GEntity entity) {
        var move = moveListeners.remove(entity);
        if (move != null) { entity.unregisterListener(EventMove.class, move); }
        var leave = leaveMapListeners.remove(entity);
        if (leave != null) { entity.unregisterListener(EventObjectLeaveMap.class, leave); }
        needCalEntities.remove(entity);
        if (inAreaEntities.remove(entity)) { triggerOut(entity); }
    }

    @Override
    public void onTriggerLeaveMap() {
        this.triggerEntity.unregisterListener(EventLeaveAoi.class, leaveAoiListener);
        this.triggerEntity.unregisterListener(EventEnterAoi.class, enterAoiListener);

        for (var entry : moveListeners.entrySet()) {
            entry.getKey().unregisterListener(EventMove.class, entry.getValue());
        }
        for (var entry : leaveMapListeners.entrySet()) { entry.getKey().unregisterListener(EventObjectLeaveMap.class, entry.getValue()); }
        moveListeners.clear();
        leaveMapListeners.clear();
        inAreaEntities.clear();
        needCalEntities.clear();
    }

    @Override
    public void onTick(long deltaMills) {
        var pending = List.copyOf(needCalEntities);
        needCalEntities.clear();
        long version = lifecycle;
        for (var entity : pending) {
            if (!active || version != lifecycle) { break; }
            if (!moveListeners.containsKey(entity)) { continue; }
            if (area.isInArea(entity.getPos())) {
                if (!inAreaEntities.contains(entity)) {
                    inAreaEntities.add(entity);
                    triggerIn(entity);
                }
            } else {
                if (inAreaEntities.contains(entity)) {
                    inAreaEntities.remove(entity);
                    triggerOut(entity);
                }
            }
        }
    }
}
