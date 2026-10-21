package state;

import behaviour.GEntity;
import manager.ILeaveMap;
import manager.IStateFinishHandle;
import manager.IUpdate;
import state.param.NormalStateParam;
import event.object.EventStateAdded;
import event.object.EventStateRemoved;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zyao on 2022/10/17 17:47
 */
public class StateManager implements IUpdate, ILeaveMap {
    private final Set<Integer> curDisableActions = new HashSet<>();
    private final Set<Integer> curProtectActions = new HashSet<>();
    private final Set<Integer> curProtectStates = new HashSet<>();
    private int fullBlockCount = 0;
    private final Set<Integer> curFullBlockAllowActions = new HashSet<>();
    private final Set<Integer> curFullBlockAllowStates = new HashSet<>();
    private final Map<Long, State<?, ?, ?>> stateMap = new HashMap<>();
    private final Map<Integer, List<Long>> stateCfgId2InstIdMap = new HashMap<>();
    private final GEntity entity;
    private final StateHost host;
    private final Set<Long> endingStates = new HashSet<>();
    private final static AtomicLong idGen = new AtomicLong();

    @Override
    public void update(long deltaMills) {
        internalUpdate();
    }

    @SuppressWarnings("unchecked")
    private <G, U, T extends StateParam<G, U>> void internalUpdate() {
        if (stateMap.isEmpty()) {
            return;
        }
        long nowMills = host.getNowMills();
        List<State<G, U, T>> endStateList = new ArrayList<>();
        List<State<G, U, T>> updateStateList = new ArrayList<>();
        for (State<?, ?, ?> state : List.copyOf(stateMap.values())) {
            if (!state.isEffect(entity, nowMills)) {
                endStateList.add((State<G, U, T>)state);
            } else if (state.updated(entity)){
                updateStateList.add((State<G, U, T>)state);
            }
        }
        endStateList.removeIf(state -> !endState(state, true, false));
        updateStateList.removeIf(state -> !stateMap.containsKey(state.stateInstId));
        if (!endStateList.isEmpty() || !updateStateList.isEmpty()) {
            reCalculate();
            notifyCompleteStateChange(List.of(), endStateList, updateStateList);
        }
    }

    public Map<Long, State<?, ?, ?>> getAllStates() {
        return Collections.unmodifiableMap(stateMap);
    }

    public Collection<Integer> getAllStateCfgIds() {
        List<Integer> stateCfgIds = new ArrayList<>();
        stateCfgId2InstIdMap.forEach((stateCfgId, instIds) -> {
            if (!instIds.isEmpty()) {
                stateCfgIds.add(stateCfgId);
            }
        });
        return stateCfgIds;
    }

    private List<Long> getInstIds(int stateCfgId) {
        return stateCfgId2InstIdMap.computeIfAbsent(stateCfgId, (__) -> new ArrayList<>());
    }

    public boolean hasState(int stateCfgId) {
        return !getInstIds(stateCfgId).isEmpty();
    }

    @SuppressWarnings("unchecked")
    public <G,  U, T extends StateParam<G, U>> State<G, U, T> getState(int stateCfgId) {
        List<Long> ids = getInstIds(stateCfgId);
        if (ids.isEmpty()) {
            return null;
        } else if (ids.size() > 1) {
            throw new RuntimeException("Cant call this function");
        } else {
            return (State<G, U, T>)stateMap.get(ids.get(0));
        }
    }

    @SuppressWarnings("unchecked")
    public <G,  U, T extends StateParam<G, U>> State<G, U, T> getState(long stateInstId) {
        return (State<G, U, T>)stateMap.get(stateInstId);
    }

    public boolean checkCanAction(int actionId) {
        if (fullBlockCount > 0) { return curFullBlockAllowActions.contains(actionId); }
        return host.isActiveAction(actionId) ? !curDisableActions.contains(actionId) : !curProtectActions.contains(actionId);
    }

//    public boolean checkCanBeAction(Eaction eaction) {
//        Eaction_Detail cfg =  ConfigMgr.getMgr().player_eaction_All.get(eaction.getId());
//        return !curProtectActions.contains(cfg.getId());
//    }

    public boolean checkCanAddState(int stateCfgId) {
        if (fullBlockCount > 0) {
            return curFullBlockAllowStates.contains(stateCfgId);
        }
        return !curProtectStates.contains(stateCfgId);
    }

    /** 当前 isFullBlock 状态的数量。大于 0 表示实体处于全阻塞状态。 */
    public int getFullBlockCount() {
        return fullBlockCount;
    }

    private Set<Integer> calculateDisableActions(StateConfig stateCfg) {
        Set<Integer> actionDisables = new HashSet<>();
        for (int disableAction : stateCfg.getDisableActions()) {
            if (!host.isActiveAction(disableAction)) {
                throw new IllegalArgumentException("disableAction:" + disableAction + " is not active");
            }
            actionDisables.add(disableAction);
        }
        return actionDisables;
    }

    private Set<Integer> calculateProtectActions(StateConfig stateCfg) {
        Set<Integer> actionProtects = new HashSet<>();
        for (int protectAction : stateCfg.getProtectOtherActions()) {
            if (host.isActiveAction(protectAction)) {
                throw new IllegalArgumentException("protectAction:" + protectAction + " is active");
            }
            actionProtects.add(protectAction);
        }
        return actionProtects;
    }

    private Set<Integer> calculateProtectStates(StateConfig stateCfg) {
        return new HashSet<>(stateCfg.getProtectStates());
    }

    public boolean addState(int stateCfgId) {
        return addState(stateCfgId, null);
    }

    public boolean addState(int stateCfgId, IStateFinishHandle endHandle) {
        return addState(stateCfgId, endHandle, -1, new StateId());
    }

    public boolean addState(int stateCfgId, IStateFinishHandle endHandle, StateId instId) {
        return addState(stateCfgId, endHandle, -1, instId);
    }

    public boolean addState(int stateCfgId, IStateFinishHandle endHandle, long lastTimeMills) {
        return addState(stateCfgId, endHandle, lastTimeMills, host.getNowMills(), new StateId());
    }

    public boolean addState(int stateCfgId, IStateFinishHandle endHandle, long lastTimeMills, StateId instId) {
        return addState(stateCfgId, endHandle, lastTimeMills, host.getNowMills(), instId);
    }

    public boolean addState(int stateCfgId, IStateFinishHandle endHandle, long lastTimeMills, long startTime, StateId instId) {
        return addState(stateCfgId, endHandle, lastTimeMills, startTime, new NormalStateParam(), instId);
    }

    public <G, U, T extends StateParam<G, U>> boolean addState(int stateCfgId, IStateFinishHandle endHandle, long lastTimeMills, T stateParam) {
        return addState(stateCfgId, endHandle, lastTimeMills, host.getNowMills(), stateParam, new StateId());
    }

    public <G, U, T extends StateParam<G, U>> boolean addState(int stateCfgId, IStateFinishHandle endHandle, long lastTimeMills, T stateParam, StateId instId) {
        return addState(stateCfgId, endHandle, lastTimeMills, host.getNowMills(), stateParam, instId);
    }

    private <G, U, T extends StateParam<G, U>> void registerListenerByInterruptAction(State<G,U,T> state, int actionInterrupt) {
        state.listeners.put(actionInterrupt, host.listenInterrupt(entity, actionInterrupt, () -> interruptEndState(state)));
    }
    /**
     * 增加状态
     * @param stateCfgId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <G,  U, T extends StateParam<G, U>> boolean addState(int stateCfgId, IStateFinishHandle endHandle, long lastTimeMills, long startTime, T stateParam, StateId instId) {
        StateConfig stateCfg = host.getStateConfig(stateCfgId);
        if (!checkCanAddState(stateCfgId)) {
            return false;
        }
        if (!getInstIds(stateCfgId).isEmpty() && !stateCfg.getIsMultiple()) {
            return false;
        }

        Set<Integer> disabled = calculateDisableActions(stateCfg);
        Set<Integer> protectedActions = calculateProtectActions(stateCfg);
        Set<Integer> protectedStates = calculateProtectStates(stateCfg);
        long nowMills = host.getNowMills();
        StateBuilder<G, U, T> stateBuilder = new StateBuilder<>();
        stateBuilder.setStateConfig(stateCfg)
                .setInstId(idGen.incrementAndGet())
                .setStartTime(startTime)
                .setExpireTimeMills(lastTimeMills == -1? -1 : nowMills + lastTimeMills)
                .setFinishHandle(endHandle)
                .setParam(stateParam);
        State<G, U, T> state = stateBuilder.buildState();

        for (int actionInterrupt : stateCfg.getInterruptSelfActions()) {
            registerListenerByInterruptAction(state, actionInterrupt);
        }

        if (stateCfg.getIsFullBlock()) {
            fullBlockCount++;
            if (fullBlockCount == 1) {
                curFullBlockAllowActions.addAll(stateCfg.getAllowActions());
                curFullBlockAllowStates.addAll(stateCfg.getAllowStates());
            } else {
                curFullBlockAllowActions.retainAll(stateCfg.getAllowActions());
                curFullBlockAllowStates.retainAll(stateCfg.getAllowStates());
            }
        }
        curDisableActions.addAll(disabled);
        curProtectActions.addAll(protectedActions);
        curProtectStates.addAll(protectedStates);

        stateMap.put(state.stateInstId, state);
        getInstIds(stateCfgId).add(state.stateInstId);
        // 状态真正加入后再抛事件，避免失败的状态创建污染监听方运行态。
        instId.value = state.stateInstId;
        entity.triggerEvent(new EventStateAdded(entity, state));
        if (stateMap.containsKey(state.stateInstId)) { notifyCompleteStateChange(List.of(state), List.of(), List.of()); }
        return true;
    }

    private void reCalculate() {
        curDisableActions.clear();
        curProtectActions.clear();
        curProtectStates.clear();
        fullBlockCount = 0;
        curFullBlockAllowActions.clear();
        curFullBlockAllowStates.clear();
        boolean first = true;
        for (var entry : stateMap.entrySet()) {
            var state = entry.getValue();
            if (state.stateCfg.getIsFullBlock()) {
                fullBlockCount++;
                if (first) {
                    curFullBlockAllowActions.addAll(state.stateCfg.getAllowActions());
                    curFullBlockAllowStates.addAll(state.stateCfg.getAllowStates());
                    first = false;
                } else {
                    curFullBlockAllowActions.retainAll(state.stateCfg.getAllowActions());
                    curFullBlockAllowStates.retainAll(state.stateCfg.getAllowStates());
                }
            }
            curDisableActions.addAll(calculateDisableActions(state.stateCfg));
            curProtectActions.addAll(calculateProtectActions(state.stateCfg));
            curProtectStates.addAll(calculateProtectStates(state.stateCfg));
        }
    }

    // 对外接口, 内部请直接调用endState
    public void cancelState(int stateCfgId) {
        cancelState(stateCfgId, true);
    }

    // 对外接口, 内部请直接调用endState
    public void cancelState(int stateCfgId, boolean isNormal) {
        var state = getState(stateCfgId);
        if (state == null) {
            return;
        }
        endState(state, isNormal, true);
    }

    // 对外接口, 内部请直接调用endState
    public void cancelState(long stateInstId) {
        cancelState(stateInstId, true);
    }

    // 对外接口, 内部请直接调用endState
    public void cancelState(long stateInstId, boolean isNormal) {
        State<?, ?, ?> state = stateMap.get(stateInstId);
        if (state == null) {
            return;
        }
        endState(state, isNormal, true);
    }

    /**
     * 重置state扩展数据 传入BState.stateParam中的bean结构
     * @param stateCfgId
     * @param updateStateParam
     */
    public <G, U, T extends StateParam<G, U>> void resetStateParam(int stateCfgId, U updateStateParam) {
        State<G, U, T> state = getState(stateCfgId);
        if (state == null) {
            return;
        }
        state.updateStateParam(updateStateParam);
    }

    private <G,U,T extends StateParam<G,U>> void unregisterListeners(State<G,U,T> state) {
        var handles = List.copyOf(state.listeners.values());
        state.listeners.clear();
        for (var handle : handles) { handle.dispose(); }
    }

    private <G,  U, T extends StateParam<G, U>> void interruptEndState(State<G, U, T> state) {
        if (!endState(state, false, false)) { return; }
        notifyCompleteStateChange(List.of(), List.of(state), List.of());
    }

    private <G, U, T extends StateParam<G, U>> boolean endState(State<G, U, T> state, boolean isNormal, boolean notify) {
        if (!stateMap.containsKey(state.stateInstId) || !endingStates.add(state.stateInstId)) { return false; }
        try {
            if (state.finishHandle != null) {
                state.finishHandle.handle(this.entity, state, isNormal);
            }
            // finishHandle 先清理业务占位，再通知监听方释放状态快照。
            entity.triggerEvent(new EventStateRemoved(entity, state));
        } finally {
            stateMap.remove(state.stateInstId);
            getInstIds(state.stateCfgId).remove(state.stateInstId);
            unregisterListeners(state);
            endingStates.remove(state.stateInstId);
            reCalculate();
        }
        if (notify) { notifyCompleteStateChange(List.of(), List.of(state), List.of()); }
        return true;
    }

    private <G,U,T extends StateParam<G,U>> void notifyCompleteStateChange(Collection<State<G,U,T>> added, Collection<State<G,U,T>> removed, Collection<State<G,U,T>> updated) {
        // 通知Gs state数据
        // 独立工程由宿主完成原广播和存储同步。
        host.onStateChanged(entity, this, List.copyOf(added), List.copyOf(removed), List.copyOf(updated));
    }

    public StateManager(GEntity entity, StateHost host) {
        this.entity = entity;
        this.host = host;
    }

    @Override
    public void onLeaveMap() {
        for (State<?, ?, ?> state : List.copyOf(stateMap.values())) {
            unregisterListeners(state);
        }
    }

}
