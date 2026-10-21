package state;

import behaviour.GEntity;
import behaviour.runtime.ListenerHandle;
import event.IMapObjectEventListener;
import event.object.EventMove;
import event.object.EventStateAdded;
import event.object.EventStateRemoved;
import math.Vector3;
import org.junit.jupiter.api.Test;
import state.param.NormalStateParam;

import java.util.*;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class StateManagerTest {
    static final class Entity implements GEntity {
        public long getInstanceId() { return 1; }
        public Vector3 getPos() { return new Vector3(0, 0, 0); }
        public long getOwnerInstId() { return 0; }
        public boolean isDestroyed() { return false; }
    }

    static final class Host implements StateHost {
        long now = 100;
        final Map<Integer, StateConfig> configs = new HashMap<>();
        final Map<Integer, List<Runnable>> mapInterrupts = new HashMap<>();
        final List<List<Long>> removedBatches = new ArrayList<>();
        final List<List<Long>> updatedBatches = new ArrayList<>();
        Consumer<StateManager> onChange = manager -> {};
        int subscriptions;
        public StateConfig getStateConfig(int id) { return Objects.requireNonNull(configs.get(id)); }
        public long getNowMills() { return now; }
        public boolean isActiveAction(int id) { return id < 100; }
        public ListenerHandle listenInterrupt(GEntity entity, int actionId, Runnable callback) {
            subscriptions++;
            if (actionId == 200) {
                mapInterrupts.computeIfAbsent(actionId, key -> new ArrayList<>()).add(callback);
                return () -> { if (mapInterrupts.get(actionId).remove(callback)) subscriptions--; };
            }
            IMapObjectEventListener<EventMove> listener = event -> callback.run();
            entity.registerListener(EventMove.class, listener);
            return new ListenerHandle() {
                boolean disposed;
                public void dispose() {
                    if (!disposed) { disposed = true; subscriptions--; entity.unregisterListener(EventMove.class, listener); }
                }
            };
        }
        void fireMapInterrupt() { for (Runnable callback : List.copyOf(mapInterrupts.getOrDefault(200, List.of()))) callback.run(); }
        public void onStateChanged(GEntity entity, StateManager manager, Collection<? extends State<?, ?, ?>> added,
                                  Collection<? extends State<?, ?, ?>> removed, Collection<? extends State<?, ?, ?>> updated) {
            removedBatches.add(removed.stream().map(s -> s.stateInstId).toList());
            updatedBatches.add(updated.stream().map(s -> s.stateInstId).toList());
            onChange.accept(manager);
        }
    }

    static StateConfig config(int id, boolean multiple, List<Integer> disabled, List<Integer> protectedActions,
                              List<Integer> protectedStates, List<Integer> interrupts, boolean full,
                              List<Integer> allowedActions, List<Integer> allowedStates) {
        return new StateConfig(id, multiple, full, disabled, protectedActions, protectedStates, allowedActions, allowedStates, interrupts);
    }
    static StateConfig normal(int id, boolean multiple) {
        return config(id, multiple, List.of(), List.of(), List.of(), List.of(), false, List.of(), List.of());
    }
    static State<?, ?, ?> add(StateManager manager, int id) {
        return instance(manager, id, null, -1, 100, new NormalStateParam());
    }

    static <D,U,T extends StateParam<D,U>> State<D,U,T> instance(StateManager manager, int id, manager.IStateFinishHandle handle, long duration, long start, T param) {
        StateId result = new StateId();
        return manager.addState(id,handle,duration,start,param,result) ? manager.getState(result.value) : null;
    }

    @Test void cancelNotificationSeesRecalculatedConstraints() {
        Host host = new Host(); Entity entity = new Entity(); StateManager manager = new StateManager(entity, host);
        host.configs.put(1, config(1, false, List.of(10), List.of(), List.of(), List.of(), false, List.of(), List.of()));
        State<?, ?, ?> state = add(manager, 1);
        host.onChange = m -> assertTrue(m.checkCanAction(10), "取消通知必须使用移除后的限制");
        manager.cancelState(state.stateInstId);
    }

    @Test void finishCallbackReentryFinishesExactlyOnce() {
        Host host = new Host(); Entity entity = new Entity(); StateManager manager = new StateManager(entity, host);
        host.configs.put(1, normal(1, false)); int[] finishes = {0}; int[] removed = {0};
        IMapObjectEventListener<EventStateRemoved> listener = e -> removed[0]++;
        entity.registerListener(EventStateRemoved.class, listener);
        try {
            State<?, ?, ?> state = instance(manager, 1, (e, s, normal) -> {
                if (++finishes[0] == 1) manager.cancelState(s.stateInstId);
            }, -1, 100, new NormalStateParam());
            manager.cancelState(state.stateInstId);
            assertEquals(1, finishes[0]); assertEquals(1, removed[0]);
            assertEquals(1, host.removedBatches.stream().filter(b -> !b.isEmpty()).count());
        } finally { entity.unregisterListener(EventStateRemoved.class, listener); }
    }

    @Test void addedIsIndexedAndRemovedFollowsFinishBeforeIndexRemoval() {
        Host host = new Host(); Entity entity = new Entity(); StateManager manager = new StateManager(entity, host);
        host.configs.put(1, normal(1, false)); List<String> events = new ArrayList<>();
        IMapObjectEventListener<EventStateAdded> added = e -> {
            assertSame(e.state, manager.getState(e.state.stateInstId)); events.add("added");
        };
        IMapObjectEventListener<EventStateRemoved> removed = e -> {
            assertSame(e.state, manager.getState(e.state.stateInstId)); events.add("removed");
        };
        entity.registerListener(EventStateAdded.class, added); entity.registerListener(EventStateRemoved.class, removed);
        try {
            assertTrue(manager.addState(1, (e, s, normal) -> { assertTrue(normal); events.add("finish"); }));
            assertFalse(manager.addState(1)); manager.cancelState(1);
            assertEquals(List.of("added", "finish", "removed"), events); assertFalse(manager.hasState(1));
        } finally {
            entity.unregisterListener(EventStateAdded.class, added); entity.unregisterListener(EventStateRemoved.class, removed);
        }
    }

    @Test void removedCallbackReentryCannotRepeatFinishOrNotification() {
        Host host = new Host(); Entity entity = new Entity(); StateManager manager = new StateManager(entity, host);
        host.configs.put(1, normal(1, false)); int[] finishes = {0}; int[] events = {0};
        IMapObjectEventListener<EventStateRemoved> listener = e -> { if (++events[0] == 1) manager.cancelState(e.state.stateInstId); };
        entity.registerListener(EventStateRemoved.class, listener);
        try {
            manager.addState(1, (e, s, normal) -> finishes[0]++); manager.cancelState(1);
            assertEquals(1, finishes[0]); assertEquals(1, events[0]);
            assertEquals(1, host.removedBatches.stream().filter(b -> !b.isEmpty()).count());
        } finally { entity.unregisterListener(EventStateRemoved.class, listener); }
    }

    @Test void ordinaryRulesUnionAndSharedEntriesRemainUntilLastStateEnds() {
        Host host = new Host(); Entity entity = new Entity(); StateManager manager = new StateManager(entity, host);
        host.configs.put(1, config(1, false, List.of(10), List.of(110), List.of(3), List.of(), false, List.of(), List.of()));
        host.configs.put(2, config(2, false, List.of(10, 20), List.of(110, 120), List.of(3, 4), List.of(), false, List.of(), List.of()));
        host.configs.put(3, normal(3, false)); add(manager, 1); add(manager, 2);
        assertFalse(manager.checkCanAction(10)); assertFalse(manager.checkCanAction(20));
        assertFalse(manager.checkCanAction(110)); assertFalse(manager.checkCanAction(120));
        assertFalse(manager.addState(3)); assertFalse(manager.checkCanAddState(4));
        manager.cancelState(1); assertFalse(manager.checkCanAction(10)); assertFalse(manager.checkCanAddState(3));
        manager.cancelState(2); assertTrue(manager.checkCanAction(10)); assertTrue(manager.checkCanAction(110));
        assertTrue(manager.checkCanAddState(3)); assertTrue(manager.addState(3));
    }

    @Test void fullBlockUsesIntersectionAndOverridesOrdinaryRules() {
        Host host = new Host(); Entity entity = new Entity(); StateManager manager = new StateManager(entity, host);
        host.configs.put(1, config(1, false, List.of(20), List.of(120), List.of(8), List.of(), false, List.of(), List.of()));
        host.configs.put(2, config(2, true, List.of(), List.of(), List.of(), List.of(), true, List.of(10, 20, 120), List.of(2, 3, 8)));
        host.configs.put(3, config(3, true, List.of(), List.of(), List.of(), List.of(), true, List.of(20, 30), List.of(2, 3, 9)));
        add(manager, 1); State<?, ?, ?> first = add(manager, 2); State<?, ?, ?> second = add(manager, 2);
        assertEquals(2, manager.getFullBlockCount()); assertTrue(manager.checkCanAction(20)); assertTrue(manager.checkCanAction(120));
        assertTrue(manager.checkCanAddState(8)); State<?, ?, ?> third = add(manager, 3);
        assertEquals(3, manager.getFullBlockCount()); assertTrue(manager.checkCanAction(20)); assertFalse(manager.checkCanAction(10));
        assertFalse(manager.checkCanAddState(8)); assertFalse(manager.checkCanAddState(9));
        manager.cancelState(third.stateInstId); assertTrue(manager.checkCanAction(10)); assertTrue(manager.checkCanAddState(8));
        manager.cancelState(first.stateInstId); assertEquals(1, manager.getFullBlockCount());
        manager.cancelState(second.stateInstId); assertEquals(0, manager.getFullBlockCount());
        assertFalse(manager.checkCanAction(20)); assertFalse(manager.checkCanAction(120)); assertFalse(manager.checkCanAddState(8));
    }

    @Test void invalidActionClassificationDoesNotLeaveIndexesConstraintsOrListeners() {
        for (boolean wrongDisable : List.of(true, false)) {
            Host host = new Host(); Entity entity = new Entity(); StateManager manager = new StateManager(entity, host);
            host.configs.put(1, config(1, false, wrongDisable ? List.of(110) : List.of(), wrongDisable ? List.of() : List.of(10),
                    List.of(), List.of(50), true, List.of(20), List.of(1)));
            assertThrows(IllegalArgumentException.class, () -> add(manager, 1));
            assertTrue(manager.getAllStates().isEmpty()); assertEquals(0, manager.getFullBlockCount());
            assertEquals(0, host.subscriptions); assertTrue(manager.checkCanAction(30)); assertTrue(host.removedBatches.isEmpty());
        }
    }

    @Test void singleAndMultipleInstancesPreserveBothIndexesAndLiveReadOnlyView() {
        Host host = new Host(); Entity entity = new Entity(); StateManager manager = new StateManager(entity, host);
        host.configs.put(1, normal(1, false)); host.configs.put(2, normal(2, true));
        Map<Long, State<?, ?, ?>> view = manager.getAllStates(); assertTrue(manager.addState(1)); assertFalse(manager.addState(1));
        State<?, ?, ?> first = add(manager, 2); State<?, ?, ?> second = add(manager, 2);
        assertEquals(3, view.size()); assertThrows(UnsupportedOperationException.class, view::clear);
        assertThrows(RuntimeException.class, () -> manager.getState(2)); assertThrows(RuntimeException.class, () -> manager.cancelState(2));
        manager.cancelState(first.stateInstId); assertSame(second, manager.getState(2)); manager.cancelState(second.stateInstId);
        assertFalse(manager.hasState(2)); assertEquals(Set.of(1), new HashSet<>(manager.getAllStateCfgIds()));
        int notices = host.removedBatches.size(); manager.cancelState(-10L); assertEquals(notices, host.removedBatches.size());
    }

    @Test void expiryUsesNowPlusDurationAndEndsAtDeadlineWithOneBatch() {
        Host host = new Host(); Entity entity = new Entity(); StateManager manager = new StateManager(entity, host);
        host.configs.put(1, normal(1, true)); List<Boolean> finishes = new ArrayList<>();
        State<?, ?, ?> first = instance(manager, 1, (e, s, normal) -> finishes.add(normal), 10, 7, new NormalStateParam());
        State<?, ?, ?> second = instance(manager, 1, (e, s, normal) -> finishes.add(normal), 10, 8, new NormalStateParam());
        State<?, ?, ?> infinite = add(manager, 1); assertEquals(110, first.expireTimeMills); assertEquals(7, first.startTime);
        host.now = 109; manager.update(9); assertEquals(3, manager.getAllStates().size());
        host.now = 110; manager.update(1); assertEquals(List.of(true, true), finishes);
        assertEquals(Set.of(first.stateInstId, second.stateInstId), new HashSet<>(host.removedBatches.getLast()));
        assertEquals(Set.of(infinite.stateInstId), manager.getAllStates().keySet());
    }

    @Test void entityAndMapInterruptionsFinishAbnormallyAndReleaseSubscriptions() {
        for (int action : List.of(50, 200)) {
            Host host = new Host(); Entity entity = new Entity(); StateManager manager = new StateManager(entity, host);
            host.configs.put(1, config(1, false, List.of(10), List.of(), List.of(), List.of(action), false, List.of(), List.of()));
            List<Boolean> finishes = new ArrayList<>(); manager.addState(1, (e, s, normal) -> finishes.add(normal));
            if (action == 50) entity.triggerEvent(new EventMove(entity)); else host.fireMapInterrupt();
            assertEquals(List.of(false), finishes); assertFalse(manager.hasState(1)); assertEquals(0, host.subscriptions);
            entity.triggerEvent(new EventMove(entity)); host.fireMapInterrupt(); assertEquals(List.of(false), finishes);
        }
    }

    @Test void leaveOnlyUnsubscribesAndCancelOrExpiryAlsoReleaseListeners() {
        Host host = new Host(); Entity entity = new Entity(); StateManager manager = new StateManager(entity, host);
        host.configs.put(1, config(1, true, List.of(), List.of(), List.of(), List.of(50, 200), false, List.of(), List.of()));
        int[] finishes = {0}; State<?, ?, ?> state = instance(manager, 1, (e, s, normal) -> finishes[0]++, -1, 100, new NormalStateParam());
        assertEquals(2, host.subscriptions); manager.onLeaveMap(); manager.onLeaveMap();
        assertEquals(0, host.subscriptions); assertSame(state, manager.getState(state.stateInstId));
        entity.triggerEvent(new EventMove(entity)); host.fireMapInterrupt(); assertEquals(0, finishes[0]);
        manager.cancelState(state.stateInstId, false); assertEquals(1, finishes[0]);
        State<?, ?, ?> cancelled = add(manager, 1); manager.cancelState(cancelled.stateInstId); assertEquals(0, host.subscriptions);
        instance(manager, 1, null, 1, 100, new NormalStateParam()); host.now = 101; manager.update(1); assertEquals(0, host.subscriptions);
    }

    static final class Param extends StateParam<String, Integer> {
        int value; int updates; boolean effect = true; boolean changed;
        public String toProtocolData() { return "value=" + value; }
        public long checkParam(Integer input) { return input < 0 ? 7 : 0; }
        public void update(Integer input) { updates++; value = input; }
        public boolean isEffect(GEntity entity, int id) { return effect; }
        public boolean updated(GEntity entity, int id) { return changed; }
    }

    @Test void parameterChecksRejectBeforeUpdateAndBatchReportsChangedAndInvalidStates() {
        Host host = new Host(); Entity entity = new Entity(); StateManager manager = new StateManager(entity, host);
        host.configs.put(1, normal(1, false)); host.configs.put(2, normal(2, false)); Param param = new Param(); Param invalid = new Param();
        assertEquals(7, param.tryUpdate(-1)); assertEquals(0, param.updates); assertEquals(0, param.tryUpdate(2));
        assertEquals("value=2", param.toProtocolData()); List<Boolean> ended = new ArrayList<>();
        State<?, ?, ?> changed = instance(manager, 1, null, -1, 100, param);
        State<?, ?, ?> expired = instance(manager, 2, (e, s, normal) -> ended.add(normal), -1, 100, invalid);
        manager.resetStateParam(1, -2); assertEquals(1, param.updates);
        manager.resetStateParam(1, 4); assertEquals(4, param.value); assertEquals(2, param.updates);
        param.changed = true; invalid.effect = false; manager.update(1);
        assertEquals(List.of(true), ended); assertEquals(List.of(expired.stateInstId), host.removedBatches.getLast());
        assertEquals(List.of(changed.stateInstId), host.updatedBatches.getLast());
    }

    @Test void builderRejectsInvalidInstanceAndDeadlineAndSetterRetainsSourceSemantics() {
        StateBuilder<Object, Object, NormalStateParam> builder = new StateBuilder<Object, Object, NormalStateParam>()
                .setStateConfig(normal(1,false)).setParam(new NormalStateParam()).setExpireTimeMills(-1);
        assertThrows(IllegalArgumentException.class, builder::buildState); builder.setInstId(1).setExpireTimeMills(0);
        assertThrows(IllegalArgumentException.class, builder::buildState); builder.setExpireTimeMills(-2);
        assertThrows(IllegalArgumentException.class, builder::buildState); builder.setExpireTimeMills(-1);
        State<Object, Object, NormalStateParam> state = builder.buildState(); state.setExpireTimeMills(0);
        assertTrue(state.isEffect(new Entity(),10)); assertFalse(state.updated(new Entity()));
        state.setExpireTimeMills(10); assertFalse(state.isEffect(new Entity(),10));
    }
}
