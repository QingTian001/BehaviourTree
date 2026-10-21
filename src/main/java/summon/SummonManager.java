package summon;

import area.AreaFan;
import behaviour.GEntity;
import summon.area.AreaVisitorListPoint;
import summon.area.EDistributeType;
import summon.create.EntityCreateStrategy;
import summon.generator.IEntityIdGenerator;
import summon.param.SummonEntityParam;
import summon.param.NormalSummonEntityParam;
import summon.posstrategy.PositionTypeStrategy;
import event.IMapObjectEventListener;
import event.object.EventObjectLeaveMap;
import java.util.function.Consumer;
import math.Vector3;

import java.util.*;


/**
 * Created by zyao on 2025/2/27 16:50
 */
public abstract class SummonManager {

    private GEntity self;
    private boolean leaving;
    private final SummonHost host;
    private final Set<GEntity> summonEntities = new HashSet<>();
    private final Map<GEntity, IMapObjectEventListener<EventObjectLeaveMap>> leaveMapListeners = new HashMap<>();
    private final Map<Integer, Set<GEntity>> summonCfgId2SummonEntities = new HashMap<>();
    private final Map<Integer, Integer> summonCfgId2SummonType = new HashMap<>();
    private final Map<GEntity, Integer> summonEntity2SummonCfgId = new HashMap<>();


    public SummonManager(GEntity self, SummonHost host) {
        this.self = self;
        this.host = host;
    }

    public GEntity getSelf() {
        return self;
    }

    public SummonHost getHost() {
        return host;
    }

    // getter
    public Set<GEntity> getSummonEntities() {
        return summonEntities;
    }

    public Map<GEntity, IMapObjectEventListener<EventObjectLeaveMap>> getLeaveMapListeners() {
        return leaveMapListeners;
    }

    public Map<Integer, Set<GEntity>> getSummonCfgId2SummonEntities() {
        return summonCfgId2SummonEntities;
    }

    public Map<Integer, Integer> getSummonCfgId2SummonType() {
        return summonCfgId2SummonType;
    }

    public Map<GEntity, Integer> getSummonEntity2SummonCfgId() {
        return summonEntity2SummonCfgId;
    }

    public void summonAndEnterMap(int summonCfgId) {
        summonAndEnterMap(summonCfgId, new NormalSummonEntityParam(), null);
    }

    public void summonAndEnterMap(int summonCfgId,  Consumer<List<GEntity>> callBack) {
        summonAndEnterMap(summonCfgId, new NormalSummonEntityParam(), callBack);
    }

    /**
     * 根据summonCfgId创建多个实体，并进入地图, param中属性会被summonCfg中的属性覆盖
     * 为了避免concurrentModifiedException, 不能在map的entity的update循环中调用
     */
    public void summonAndEnterMap(int summonCfgId, SummonEntityParam param, Consumer<List<GEntity>> callBack) {
        if (self == null || leaving) { throw new IllegalStateException("召唤管理器已离场"); }
        int summonType = param.getSummonType();
        SummonConfig summonCfg = host.getSummonConfig(summonCfgId);
        param.setAoiPublic(summonCfg.getAoiPublic());
        if (summonCfg.getLifetime() > 0) {
            param.setLeaveMapTimeStampMills(host.getNowMills() + summonCfg.getLifetime());
        }
        IEntityIdGenerator generator = IEntityIdGenerator.create(summonCfg.getSummonType());
        int summonCount = param.getOverrideSummonCount();
        if (summonCount <= 0)
            summonCount = summonCfg.getNum();
        List<Integer> summonIdxs = generator.generateIndexes(summonCfg.getUnits(), summonCount);

        PositionTypeStrategy positionStrategy = Util.createPositionStrategy(summonCfg.getPositionType(), self, summonCfg, host);

        Vector3 basePos = positionStrategy.calculateBasePosition();
        float baseDir = positionStrategy.getBaseDir();
        if (summonCfg.getSummonAngle() < 0 || summonCfg.getSummonAngle() > 360) {
            throw new IllegalArgumentException("unexpected summon angle:" + summonCfg.getSummonAngle());
        }
        AreaFan area = new AreaFan(basePos, summonCfg.getRadius(), baseDir + summonCfg.getBaseOffsetAngle(), summonCfg.getSummonAngle()/2);
        AreaVisitorListPoint visitor = AreaVisitorListPoint.create(EDistributeType.get(summonCfg.getDistributionType()), summonCount);
        visitor.visit(area);
        List<Vector3> points = visitor.getPoints();

        List<Float> dirs = new ArrayList<>();
        ArrayList<Vector3> addHeightPoints = new ArrayList<>();
        for (Vector3 point : points) {
            dirs.add(Util.getSummonAngle(basePos, baseDir, summonCfg, point));
            addHeightPoints.add(new Vector3(point.x, point.y + summonCfg.getHeight(), point.z));
        }

        // 迁移说明：原 Navmesh 处理属于宿主创建策略，以下保留来源分支注释供对照。
        // newPointList, navmesh查找类型, oldPointList
        if (self == null || self.isDestroyed()) { return; }
        List<GEntity> created = new ArrayList<>();
        EntityCreateStrategy strategy = host.getEntityCreateStrategy(summonCfg.getObjType());
        for (int i = 0; i < summonIdxs.size(); i++) {
            GEntity entity = strategy.createIfNecessary(summonCfg.getUnits().get(summonIdxs.get(i)), addHeightPoints.get(i).Clone(), dirs.get(i), summonCfgId, param);
            if (entity != null) { created.add(entity); }
        }
        if (!created.isEmpty()) { summonCfgId2SummonType.put(summonCfgId, summonType); }
        for (GEntity entity : created) {
            addSummonEntity(entity, param);
            summonCfgId2SummonEntities.computeIfAbsent(summonCfgId, k -> new HashSet<>()).add(entity);
            summonEntity2SummonCfgId.put(entity, summonCfgId);
            host.enterMap(entity);
        }
        // 绑定操作
        positionStrategy.checkAndBindSummonEntity(created);
        if (callBack != null) { callBack.accept(created); }
        // 使用Navmesh且不是岸边类型
    }

    public void activeRemoveSummonEntities(int summonCfgId) {
        Set<GEntity> entities = summonCfgId2SummonEntities.get(summonCfgId);
        if (entities == null) {
            return;
        }
        int summonType = summonCfgId2SummonType.get(summonCfgId);
        List<GEntity> entitiesToRemove = new ArrayList<>(entities);
        for (GEntity entity: entitiesToRemove) {
            activeRemoveSummonEntity(entity, summonType);
        }
        summonCfgId2SummonEntities.remove(summonCfgId);
    }

    protected void onAddSummonEntity(GEntity entity, SummonEntityParam param) {

    }

    // 加入玩家的专属对象, 玩家离开时全部清理, 同时要监听专属对象离开地图事件
    public void addSummonEntity(GEntity entity, SummonEntityParam param) {
        if (self == null || leaving) { throw new IllegalStateException("召唤管理器已离场"); }
        if (!summonEntities.add(entity)) { return; }

        IMapObjectEventListener<EventObjectLeaveMap> eventListener = event -> summonEntityLeaveMap(entity);
        entity.registerListener(EventObjectLeaveMap.class, eventListener);
        leaveMapListeners.put(entity, eventListener);

        host.configureSummon(entity, self, param.isAoiPublic(), param.getLeaveMapTimeStampMills());

        onAddSummonEntity(entity, param);
    }

    protected void onActiveRemoveSummonEntity(GEntity entity, int summonType) {

    }

    // 业务按照移除类型和参数移除, 移除后要清理掉对应的数据
    public void activeRemoveSummonEntity(GEntity entity, int summonType) {
        //internalRemoveSummonEntity(entity);
        host.leaveMap(entity);
        onActiveRemoveSummonEntity(entity, summonType);
    }


    protected void onSummonEntityLeaveMap(GEntity entity) {

    }

    protected final void summonEntityLeaveMap(GEntity entity) {
        internalRemoveSummonEntity(entity);
        onSummonEntityLeaveMap(entity);
    }

    private void internalRemoveSummonEntity(GEntity entity) {
        summonEntities.remove(entity);
        Integer summonCfgId = summonEntity2SummonCfgId.remove(entity);
        if (summonCfgId != null) {
            Set<GEntity> group = summonCfgId2SummonEntities.get(summonCfgId);
            group.remove(entity);
            if (group.isEmpty()) {
                summonCfgId2SummonEntities.remove(summonCfgId);
                summonCfgId2SummonType.remove(summonCfgId);
            }
        }

        IMapObjectEventListener<EventObjectLeaveMap> listener = leaveMapListeners.remove(entity);
        if (listener != null)  {
            entity.unregisterListener(EventObjectLeaveMap.class, listener);
        }
    }

    protected void onOwnerLeaveMap() {

    }

    public void ownerLeaveMap() {
        if (self == null || leaving) { return; }
        leaving = true;
        List<GEntity> destroyEntities = new ArrayList<>();
        for (var summonEntry : List.copyOf(summonCfgId2SummonEntities.entrySet())) {
            int summonCfgId = summonEntry.getKey();
            SummonConfig summonCfg = host.getSummonConfig(summonCfgId);
            if (summonCfg.getDestroyWithOwner()) {
                destroyEntities.addAll(summonEntry.getValue());
            }
        }

        for (GEntity entity : destroyEntities) {
            internalRemoveSummonEntity(entity);
            host.leaveMap(entity);
        }

        for (var summonEntry : List.copyOf(summonCfgId2SummonEntities.entrySet())) {
            int summonCfgId = summonEntry.getKey();
            SummonConfig summonCfg = host.getSummonConfig(summonCfgId);
            long summonTimeoutMills = summonCfg.getDestroyTimeMsAfterOwner() + host.getNowMills();

            for (GEntity entity : List.copyOf(summonEntry.getValue())) {
                host.setOwnerLeaveDeadline(entity, summonTimeoutMills);
            }
        }
        for (GEntity entity : List.copyOf(summonEntities)) { host.setOwner(entity, null); }
        onOwnerLeaveMap();

        for (GEntity entity : List.copyOf(summonEntities)) { internalRemoveSummonEntity(entity); }
        summonCfgId2SummonEntities.clear();
        summonCfgId2SummonType.clear();
        self = null; // 防止内存泄露
    }
}

