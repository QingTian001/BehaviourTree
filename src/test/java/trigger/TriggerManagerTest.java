package trigger;

import aoi.Grid;
import aoi.GridMapAoiManager;
import area.*;
import behaviour.GEntity;
import event.object.*;
import math.Vector3;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import testsupport.TreeFixture.Entity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TriggerManagerTest {
    @ParameterizedTest @CsvSource({"-10,-1", "-0.1,-1", "0,0", "9.9,0", "10,1"})
    void gridCoordinatesUseFloorAtZeroAndNegativeBoundaries(float position, int expected) {
        assertEquals(expected,Grid.getAoiGridAxis(position,10));
    }

    @ParameterizedTest @CsvSource({"false", "true"})
    void duplicateLifecycleAndAoiEnterDoNotDuplicateSubscriptions(boolean grid) {
        Host host = new Host(grid); Entity observer = entity(1,25,25), observed = entity(2,25,25);
        host.add(observed); var manager=TriggerManager.create(host,observer,area(),List.of(1));
        var calls=listen(manager); manager.onEnterMap(); int listeners=observed.listenerCount();
        manager.onEnterMap(); observer.triggerEvent(new EventEnterAoi(observer,observed)); host.tick(manager);
        assertEquals(listeners,observed.listenerCount()); assertTrue(calls.isEmpty());
        manager.onLeaveMap(); manager.onLeaveMap(); assertEquals(0,observed.listenerCount());
    }

    @ParameterizedTest @CsvSource({"false", "true"})
    void callbackReentryStopsThePreviousTickSnapshot(boolean grid) {
        Host host=new Host(grid); Entity observer=entity(1,25,25),first=entity(2,25,25),second=entity(3,25,25);
        var manager=TriggerManager.create(host,observer,area(),List.of(1)); List<GEntity> calls=new ArrayList<>();
        manager.addTriggerListener(new ITriggerListener() {
            public void triggerIn(GEntity entity) {
                calls.add(entity); manager.onLeaveMap(); host.seeing.clear();
                if(grid) { host.grids.entities.clear(); } manager.onEnterMap();
            }
            public void triggerOut(GEntity entity) { fail("退场不应继续分派旧帧事件"); }
        });
        manager.onEnterMap(); host.enter(observer,first); host.enter(observer,second); host.tick(manager); host.tick(manager);
        assertEquals(1,calls.size()); assertEquals(0,first.listenerCount()); assertEquals(0,second.listenerCount()); manager.onLeaveMap();
    }
    static final class Host implements TriggerHost {
        long now;
        final List<GEntity> seeing = new ArrayList<>();
        final TestGridProvider grids;
        Host(boolean grid) { grids = grid ? new TestGridProvider() : null; }
        public long getNowMills() { return now; }
        public Iterable<? extends GEntity> getSeeingEntities(GEntity observer) { return seeing; }
        public int getEntityType(GEntity entity) { return entity.getInstanceId() == 99 ? 2 : 1; }
        public GridMapAoiManager getGridMapAoiManager() { return grids; }
        void add(Entity entity) { seeing.add(entity); if (grids != null) grids.entities.add(entity); }
        void enter(Entity observer, Entity entity) { add(entity); observer.triggerEvent(new EventEnterAoi(observer, entity)); }
        void leave(Entity observer, Entity entity) {
            seeing.remove(entity);
            if (grids != null) grids.entities.remove(entity);
            observer.triggerEvent(new EventLeaveAoi(observer, entity));
        }
        void tick(TriggerManager manager) { now += 300; manager.tick(300); }
    }

    record Cell(int getGridX, int getGridZ, int level) implements Grid {
        public int getAoiLevel() { return level; }
        public Vector3 getLeftBottom() { return pos(getGridX * 10, getGridZ * 10); }
        public Vector3 getLeftTop() { return pos(getGridX * 10, getGridZ * 10 + 10); }
        public Vector3 getRightTop() { return pos(getGridX * 10 + 10, getGridZ * 10 + 10); }
        public Vector3 getRightBottom() { return pos(getGridX * 10 + 10, getGridZ * 10); }
    }

    static final class TestGridProvider implements GridMapAoiManager {
        final List<GEntity> entities = new ArrayList<>();
        public int getDefaultAoiLevel() { return 7; }
        public int getGridLength(int level) { return 10; }
        public Grid getGrid(int x, int z, int level) { return new Cell(x, z, level); }
        public Grid getCurrentGrid(GEntity entity, int level) {
            return getGrid(Grid.getAoiGridAxis(entity.getPos().x, 10), Grid.getAoiGridAxis(entity.getPos().z, 10), level);
        }
        public Iterable<? extends GEntity> getGridEntities(Grid grid) {
            return entities.stream().filter(entity -> getCurrentGrid(entity, 7).equals(grid)).toList();
        }
    }

    static Vector3 pos(float x, float z) { return new Vector3(x, 0, z); }
    static Entity entity(long id, float x, float z) { Entity entity = new Entity(id); entity.position = pos(x, z); return entity; }
    static Area area() { return new AreaCircle(pos(25, 25), 25); }
    static List<String> listen(TriggerManager manager) {
        List<String> calls = new ArrayList<>();
        manager.addTriggerListener(new ITriggerListener() {
            public void triggerIn(GEntity entity) { calls.add("in:" + entity.getInstanceId()); }
            public void triggerOut(GEntity entity) { calls.add("out:" + entity.getInstanceId()); }
        });
        return calls;
    }

    @ParameterizedTest @CsvSource({"false", "true"})
    void initialPopulationIsSilentAndMovementRespects300Milliseconds(boolean grid) {
        Host host = new Host(grid); Entity observer = entity(1, 25, 25), moving = entity(2, 25, 25);
        host.add(moving);
        TriggerManager manager = TriggerManager.create(host, observer, area(), List.of(1));
        List<String> calls = listen(manager); manager.onEnterMap();
        assertTrue(calls.isEmpty());
        moving.position = pos(100, 25); moving.triggerEvent(new EventMove(moving));
        if (grid) moving.triggerEvent(new EventCrossAoiGrid(moving, host.grids.getGrid(2, 2, 7), host.grids.getGrid(10, 2, 7)));
        host.now = 299; manager.tick(299); assertTrue(calls.isEmpty());
        host.now = 300; manager.tick(1); assertEquals(List.of("out:2"), calls);
        manager.onLeaveMap();
    }

    @ParameterizedTest @CsvSource({"false", "true"})
    void stationaryAoiEnterTriggersWithoutSubsequentMovement(boolean grid) {
        Host host = new Host(grid); Entity observer = entity(1, 25, 25), entering = entity(2, 25, 25);
        TriggerManager manager = TriggerManager.create(host, observer, area(), List.of(1));
        List<String> calls = listen(manager); manager.onEnterMap();
        host.enter(observer, entering); host.tick(manager);
        assertEquals(List.of("in:2"), calls);
        manager.onLeaveMap();
    }

    @ParameterizedTest @CsvSource({"false", "true"})
    void aoiLeaveEmitsOutExactlyOnceAndDropsPendingMovement(boolean grid) {
        Host host = new Host(grid); Entity observer = entity(1, 25, 25), observed = entity(2, 25, 25);
        host.add(observed);
        TriggerManager manager = TriggerManager.create(host, observer, area(), List.of(1));
        List<String> calls = listen(manager); manager.onEnterMap();
        observed.triggerEvent(new EventMove(observed));
        host.leave(observer, observed); host.leave(observer, observed); host.tick(manager);
        assertEquals(List.of("out:2"), calls); assertEquals(0, observed.listenerCount());
        manager.onLeaveMap();
    }

    @ParameterizedTest @CsvSource({"false", "true"})
    void observedEntityLeaveMapEmitsOutAndReleasesSubscriptions(boolean grid) {
        Host host = new Host(grid); Entity observer = entity(1, 25, 25), observed = entity(2, 25, 25);
        host.add(observed);
        TriggerManager manager = TriggerManager.create(host, observer, area(), List.of(1));
        List<String> calls = listen(manager); manager.onEnterMap();
        host.seeing.clear(); if (grid) host.grids.entities.clear();
        observed.triggerEvent(new EventObjectLeaveMap(observed)); host.tick(manager);
        assertEquals(List.of("out:2"), calls); assertEquals(0, observed.listenerCount());
        manager.onLeaveMap();
    }

    @ParameterizedTest @CsvSource({"inner,inner", "inner,interact", "inner,outer", "interact,inner", "interact,interact", "interact,outer", "outer,inner", "outer,interact", "outer,outer"})
    void allNineGridTransitionsReconcileMembershipWithoutMove(String from, String to) {
        Host host = new Host(true); Entity observer = entity(1, 25, 25), observed = entity(2, 25, 25);
        observed.position = switch (from) { case "inner" -> pos(25, 25); case "interact" -> pos(1, 1); default -> pos(65, 25); };
        host.add(observed);
        TriggerManager manager = TriggerManager.create(host, observer, area(), List.of(1));
        List<String> calls = listen(manager); manager.onEnterMap();
        Grid leave = host.grids.getCurrentGrid(observed, 7);
        observed.position = switch (to) { case "inner" -> pos(35, 25); case "interact" -> pos(5, 25); default -> pos(75, 25); };
        observed.triggerEvent(new EventCrossAoiGrid(observed, leave, host.grids.getCurrentGrid(observed, 7)));
        host.tick(manager);
        boolean before = from.equals("inner"), after = !to.equals("outer");
        assertEquals(before == after ? List.of() : List.of(after ? "in:2" : "out:2"), calls);
        manager.onLeaveMap();
        assertEquals(0, observed.listenerCount());
    }

    @Test void outerGridCrossListenerIsReleasedOnObserverLeave() {
        Host host = new Host(true); Entity observer = entity(1, 25, 25), outer = entity(2, 65, 25);
        host.add(outer);
        TriggerManager manager = TriggerManager.create(host, observer, area(), List.of(1));
        manager.onEnterMap(); assertTrue(outer.listenerCount() > 0);
        manager.onLeaveMap(); assertEquals(0, outer.listenerCount()); assertEquals(0, observer.listenerCount());
    }

    @ParameterizedTest @CsvSource({"false", "true"})
    void reentryClearsOldMembershipAndPendingDirtyEntities(boolean grid) {
        Host host = new Host(grid); Entity observer = entity(1, 25, 25), observed = entity(2, 25, 25);
        host.add(observed);
        TriggerManager manager = TriggerManager.create(host, observer, area(), List.of(1));
        List<String> calls = listen(manager); manager.onEnterMap(); observed.triggerEvent(new EventMove(observed));
        manager.onLeaveMap(); assertEquals(0, observed.listenerCount());
        host.seeing.clear(); if (grid) host.grids.entities.clear();
        manager.onEnterMap(); host.tick(manager); assertTrue(calls.isEmpty());
        host.enter(observer, observed); host.tick(manager); assertEquals(List.of("in:2"), calls);
        manager.onLeaveMap();
    }

    @ParameterizedTest @CsvSource({"false", "true"})
    void callbacksCanRemoveThemselvesAndObservedEntities(boolean grid) {
        Host host = new Host(grid); Entity observer = entity(1, 25, 25), first = entity(2, 25, 25), second = entity(3, 25, 25);
        TriggerManager manager = TriggerManager.create(host, observer, area(), List.of(1));
        List<String> calls = new ArrayList<>();
        manager.addTriggerListener(new ITriggerListener() {
            public void triggerIn(GEntity entity) {
                calls.add("in:" + entity.getInstanceId()); manager.removeTriggerListener(this);
                host.leave(observer, first); host.leave(observer, second);
            }
            public void triggerOut(GEntity entity) { calls.add("out:" + entity.getInstanceId()); }
        });
        manager.onEnterMap(); host.enter(observer, first); host.enter(observer, second);
        assertDoesNotThrow(() -> host.tick(manager));
        assertEquals(1, calls.size()); assertEquals(0, first.listenerCount()); assertEquals(0, second.listenerCount());
        manager.onLeaveMap();
    }

    @ParameterizedTest @CsvSource({"false", "true"})
    void entityTypeFilteringIgnoresInitialAndLaterUncaredObjects(boolean grid) {
        Host host = new Host(grid); Entity observer = entity(1, 25, 25), ignored = entity(99, 25, 25);
        host.add(ignored); TriggerManager manager = TriggerManager.create(host, observer, area(), List.of(1));
        List<String> calls = listen(manager); manager.onEnterMap(); host.enter(observer, ignored); host.tick(manager);
        assertTrue(calls.isEmpty()); assertEquals(0, ignored.listenerCount()); manager.onLeaveMap();
    }

    @Test void visitorUsesProviderLevelAndClassifiesCircleAndRectangleCorners() {
        TestGridProvider provider = new TestGridProvider();
        AreaAoiGridVisitor visitor = new AreaAoiGridVisitor(provider, 7); area().accept(visitor);
        assertTrue(visitor.getInnerGrids().contains(provider.getGrid(2, 2, 7)));
        assertTrue(visitor.getInteractGrids().contains(provider.getGrid(0, 0, 7)));
        assertFalse(visitor.getInnerGrids().contains(provider.getGrid(0, 0, 7)));
        new AreaRectangle(pos(10, 10), pos(30, 30)).accept(visitor);
        assertTrue(visitor.getInnerGrids().contains(provider.getGrid(1, 1, 7)));
        assertTrue(visitor.getInteractGrids().contains(provider.getGrid(3, 3, 7)));
        assertEquals(4, visitor.getInnerGrids().size());
    }

    @Test void factoryFallsBackToCommonForUnsupportedGridShapes() {
        Host host = new Host(true); Entity observer = entity(1, 25, 25);
        assertInstanceOf(GridTriggerManager.class, TriggerManager.create(host, observer, area(), List.of(1)));
        assertInstanceOf(CommonTriggerManager.class, TriggerManager.create(host, observer, new AreaRectangle(pos(25, 25), pos(0, -1), 10, 10), List.of(1)));
        assertInstanceOf(CommonTriggerManager.class, TriggerManager.create(host, observer, new AreaOnePoint(pos(25, 25), 0), List.of(1)));
    }
}
