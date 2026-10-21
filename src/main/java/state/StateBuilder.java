package state;

import manager.IStateFinishHandle;

/**
 * Created by zyao on 2024/3/4 11:46
 */
public class StateBuilder<D, U, T extends StateParam<D, U>> {
    private long instId;
    private StateConfig stateConfig;
    private IStateFinishHandle finishHandle;
    private long expireTimeMills;
    private long startTime;
    private T param;

    public int getStateCfgId() {
        return stateConfig.getId();
    }

    public StateConfig getStateConfig() { return stateConfig; }

    public StateBuilder<D, U, T> setStateConfig(StateConfig stateConfig) {
        this.stateConfig = stateConfig;
        return this;
    }

    public IStateFinishHandle getFinishHandle() {
        return finishHandle;
    }

    public StateBuilder<D, U, T> setFinishHandle(IStateFinishHandle finishHandle) {
        this.finishHandle = finishHandle;
        return this;
    }

    public long getExpireTimeMills() {
        return expireTimeMills;
    }

    public StateBuilder<D, U, T> setExpireTimeMills(long expireTimeMills) {
        this.expireTimeMills = expireTimeMills;
        return this;
    }

    public long getStartTime() {
        return startTime;
    }

    public StateBuilder<D, U, T> setStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public T getParam() {
        return param;
    }

    public StateBuilder<D, U, T> setParam(T param) {
        this.param = param;
        return this;
    }

    public long getInstId() {
        return instId;
    }

    public StateBuilder<D, U, T> setInstId(long instId) {
        this.instId = instId;
        return this;
    }

    public  State<D, U, T> buildState() {
        return new State<>(this);
    }
}
