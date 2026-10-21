package trigger;

import aoi.Grid;
import aoi.GridMapAoiManager;
import area.Area;
import behaviour.GEntity;
import event.IMapObjectEventListener;
import event.object.EventCrossAoiGrid;
import event.object.EventEnterAoi;
import event.object.EventLeaveAoi;
import event.object.EventMove;
import event.object.EventObjectLeaveMap;

import java.util.*;

/**
 * Created by zyao on 2025/2/8 11:48
 *
 */
public class GridTriggerManager extends TriggerManager{
    private IMapObjectEventListener<EventLeaveAoi> leaveAoiListener;
    private IMapObjectEventListener<EventEnterAoi> enterAoiListener;
    private final Map<GEntity, IMapObjectEventListener<EventMove>> moveListeners = new HashMap<>();
    private final Map<GEntity, IMapObjectEventListener<EventCrossAoiGrid>> crossAoiGridListeners = new HashMap<>();
    private final Map<GEntity, IMapObjectEventListener<EventObjectLeaveMap>> leaveMapListeners = new HashMap<>();
    private final Set<GEntity> innerAreaEntities = new HashSet<>();
    private final Set<GEntity> interactAreaEntities = new HashSet<>();
    private final Set<GEntity> innerAndInteractAreaEntities = new HashSet<>();
    private final Set<GEntity> inAreaEntities = new HashSet<>(); // tick时, 用于计算triggerIn, triggerout事件的依据
    private final Set<GEntity> needCalMoveEntities = new HashSet<>();
    private final Set<Grid> innerGrids = new HashSet<>(); // 只关注AoiLevel为default的格子
    private final Set<Grid> interactGrids = new HashSet<>(); // 只关注AoiLevel为default的格子
    private final GridMapAoiManager gridMapAoiManager;

    GridTriggerManager(TriggerHost host, GEntity triggerEntity, Area area, List<Integer> caredObjTypes) {
        super(host, triggerEntity, area, caredObjTypes);
        gridMapAoiManager = host.getGridMapAoiManager();
    }

    private void calculateRelatedAoiGrids() {
        AreaAoiGridVisitor visitor = new AreaAoiGridVisitor(gridMapAoiManager, gridMapAoiManager.getDefaultAoiLevel());
        area.accept(visitor);
        innerGrids.addAll(visitor.getInnerGrids());
        interactGrids.addAll(visitor.getInteractGrids());
    }

    private boolean isInArea(GEntity entity) {
        if (innerAreaEntities.contains(entity)) {
            return true;
        }
        if (interactAreaEntities.contains(entity)) {
            return area.isInArea(entity.getPos());
        }
        return false;
    }

    private void initMoveListener(GEntity observedEntity) {
        if (moveListeners.containsKey(observedEntity)) { return; }
        getLogger().debug("initMoveListener trigger:{} moveEntity:{}, moveEntityType:{}", triggerEntity.getInstanceId(), observedEntity.getInstanceId(), host.getEntityType(observedEntity));
        IMapObjectEventListener<EventMove> listener = event -> {
            // 标脏
            needCalMoveEntities.add(observedEntity);
        };

        observedEntity.registerListener(EventMove.class, listener);
        moveListeners.put(observedEntity, listener);
    }

    private void initCrossAoiGridListener(GEntity observedEntity) {
        if (crossAoiGridListeners.containsKey(observedEntity)) { return; }
        getLogger().debug("initCrossAoiGridListener trigger:{} moveEntity:{}, moveEntityType:{}", triggerEntity.getInstanceId(), observedEntity.getInstanceId(), host.getEntityType(observedEntity));
        IMapObjectEventListener<EventCrossAoiGrid> listener = event -> {
             /*这里按说应该可以不考虑进出视野事件, 这依赖于进出视野事件后发生, 列表
              leaveGrid  enterGrid        操作方式

              innerGrid  innerGrid        nothing
              innerGrid  interactGrid     remove from innerAreaEntities, add to interactAreaEntities
              innerGrid  outerGrid        remove from innerAreaEntities and innerAndInteractAreaEntities, removeMoveListener

              interactGrid  innerGrid     remove from interactAreaEntities, add to innerAreaEntities
              interactGrid  outerGrid     remove from interactAreaEntities and innerAndInteractAreaEntities, removeMoveListener
              interactGrid  interactGrid  nothing

              outerGrid  innerGrid        add to innerAreaEntities and innerAndInteractAreaEntities, initMoveListener
              outerGrid  outerGrid        nothing
              outerGrid  interactGrid     add to interactAreaEntities and innerAndInteractAreaEntities, initMoveListener
             */
            getLogger().debug("crossAoiGridListener trigger:{} moveEntity:{}, moveEntityType:{}, event:{}", triggerEntity.getInstanceId(), observedEntity.getInstanceId(), host.getEntityType(observedEntity), event);
            if (innerGrids.contains(event.leaveGrid)) {
                if (innerGrids.contains(event.enterGrid)) {
                    // do nothing
                } else if (interactGrids.contains(event.enterGrid)) {
                    innerAreaEntities.remove(observedEntity);
                    interactAreaEntities.add(observedEntity);
                } else {
                    innerAreaEntities.remove(observedEntity);
                    observedEntity.unregisterListener(EventMove.class, moveListeners.get(observedEntity));
                    moveListeners.remove(observedEntity);
                    innerAndInteractAreaEntities.remove(observedEntity);
                }
            } else if (interactGrids.contains(event.leaveGrid)) {
                if (innerGrids.contains(event.enterGrid)) {
                    interactAreaEntities.remove(observedEntity);
                    innerAreaEntities.add(observedEntity);
                } else if (interactGrids.contains(event.enterGrid)) {
                    // do nothing
                } else {
                    interactAreaEntities.remove(observedEntity);
                    observedEntity.unregisterListener(EventMove.class, moveListeners.get(observedEntity));
                    moveListeners.remove(observedEntity);
                    innerAndInteractAreaEntities.remove(observedEntity);
                }
            } else { // leaveGrid is outerGrid
                if (innerGrids.contains(event.enterGrid)) {
                    innerAndInteractAreaEntities.add(observedEntity);
                    initMoveListener(observedEntity);
                    innerAreaEntities.add(observedEntity);
                } else if (interactGrids.contains(event.enterGrid)) {
                    innerAndInteractAreaEntities.add(observedEntity);
                    initMoveListener(observedEntity);
                    interactAreaEntities.add(observedEntity);
                } else {
                    // do nothing
                }
            }
            needCalMoveEntities.add(observedEntity);
        };

        observedEntity.registerListener(EventCrossAoiGrid.class, listener);
        crossAoiGridListeners.put(observedEntity, listener);
        IMapObjectEventListener<EventObjectLeaveMap> leave = event -> removeEntity(observedEntity);
        observedEntity.registerListener(EventObjectLeaveMap.class, leave);
        leaveMapListeners.put(observedEntity, leave);
    }

    private void initAreaEntities() {
        Set<GEntity> tmpInnerAreaEntities = new HashSet<>();
        Set<GEntity> tmpInteractAreaEntities = new HashSet<>();
        Set<GEntity> tmpInnerAndInteractAreaEntities = new HashSet<>();
        for (Grid grid : innerGrids) {
            for (GEntity entity : gridMapAoiManager.getGridEntities(grid)) {
                if (notCaredObjType(entity)) {
                    continue;
                }
                tmpInnerAreaEntities.add(entity);
                tmpInnerAndInteractAreaEntities.add(entity);
            }
        }
        for (Grid grid : interactGrids) {
            for (GEntity entity : gridMapAoiManager.getGridEntities(grid)) {
                if (notCaredObjType(entity)) {
                    continue;
                }
                tmpInteractAreaEntities.add(entity);
                tmpInnerAndInteractAreaEntities.add(entity);
            }
        }

        // 对视野内的对象先执行一次计算, 看到的对象, 就是要去监听事件的对象
        var observerList = seeingEntities();
        Set<GEntity> observerSet = new HashSet<>(observerList);

        // 修剪下对象数据, 以防格子超出了视野
        for (GEntity entity : tmpInnerAndInteractAreaEntities) {
            if (observerSet.contains(entity)) {
                innerAndInteractAreaEntities.add(entity);
            }
        }
        for (GEntity entity : tmpInnerAreaEntities) {
            if (observerSet.contains(entity)) {
                innerAreaEntities.add(entity);
                inAreaEntities.add(entity);
            }
        }
        for (GEntity entity : tmpInteractAreaEntities) {
            if (observerSet.contains(entity)) {
                interactAreaEntities.add(entity);
                if (area.isInArea(entity.getPos())) {
                    inAreaEntities.add(entity);
                }
            }
        }

        for (GEntity observer : observerList) {
            if (notCaredObjType(observer)) {
                continue;
            }
            // 如果不在inner和interact区域的格子, 则只初始化跨格事件监听
            if (innerAndInteractAreaEntities.contains(observer)) {
                initMoveListener(observer);
            }
            initCrossAoiGridListener(observer);
        }
    }

    private void removeEntity(GEntity entity) {
        innerAreaEntities.remove(entity);
        interactAreaEntities.remove(entity);
        innerAndInteractAreaEntities.remove(entity);
        boolean wasInside = inAreaEntities.remove(entity);
        needCalMoveEntities.remove(entity);
        if (moveListeners.containsKey(entity)) {
            entity.unregisterListener(EventMove.class, moveListeners.get(entity));
            moveListeners.remove(entity);
        }
        if (crossAoiGridListeners.containsKey(entity)) {
            entity.unregisterListener(EventCrossAoiGrid.class, crossAoiGridListeners.get(entity));
            crossAoiGridListeners.remove(entity);
        }
        var leave = leaveMapListeners.remove(entity);
        if (leave != null) { entity.unregisterListener(EventObjectLeaveMap.class, leave); }
        if (wasInside) { triggerOut(entity); }
    }

    private void addEntity(GEntity entity) {
        if (crossAoiGridListeners.containsKey(entity)) { return; }
        needCalMoveEntities.add(entity);
        Grid entityGrid = gridMapAoiManager.getCurrentGrid(entity, gridMapAoiManager.getDefaultAoiLevel());
        if (innerGrids.contains(entityGrid)) {
            innerAreaEntities.add(entity);
            innerAndInteractAreaEntities.add(entity);
            initMoveListener(entity);
            initCrossAoiGridListener(entity);
        } else if (interactGrids.contains(entityGrid)) {
            interactAreaEntities.add(entity);
            innerAndInteractAreaEntities.add(entity);
            initCrossAoiGridListener(entity);
            initMoveListener(entity);
        } else {
            initCrossAoiGridListener(entity);
        }
    }

    private void initSelfAoiListener() {
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
            addEntity(event.enterEntity);
        };
        this.triggerEntity.registerListener(EventEnterAoi.class, enterAoiListener);
    }

    @Override
    protected void onTriggerEnterMap() {
        calculateRelatedAoiGrids();
        initSelfAoiListener();
        initAreaEntities();
    }

    @Override
    protected void onTriggerLeaveMap() {
        // 清理自身的监听器
        triggerEntity.unregisterListener(EventLeaveAoi.class, leaveAoiListener);
        triggerEntity.unregisterListener(EventEnterAoi.class, enterAoiListener);
        // 清理对象管理容器和对象的监听器
        for (var entry : moveListeners.entrySet()) { entry.getKey().unregisterListener(EventMove.class, entry.getValue()); }
        for (var entry : crossAoiGridListeners.entrySet()) { entry.getKey().unregisterListener(EventCrossAoiGrid.class, entry.getValue()); }
        for (var entry : leaveMapListeners.entrySet()) { entry.getKey().unregisterListener(EventObjectLeaveMap.class, entry.getValue()); }
        leaveMapListeners.clear();
        needCalMoveEntities.clear();
        innerAndInteractAreaEntities.clear();
        innerAreaEntities.clear();
        interactAreaEntities.clear();
        moveListeners.clear();
        crossAoiGridListeners.clear();
        innerGrids.clear();
        interactGrids.clear();
        inAreaEntities.clear();
    }

    @Override
    protected void onTick(long deltaMills) {
        var pending = List.copyOf(needCalMoveEntities);
        needCalMoveEntities.clear();
        long version = lifecycle;
        for (var entity : pending) {
            if (!active || version != lifecycle) { break; }
            if (!crossAoiGridListeners.containsKey(entity)) { continue; }
            if (isInArea(entity)) {
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
