package state;

import behaviour.GEntity;
import manager.IStateFinishHandle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyao on 2022/3/14 12:22
 */
public class State<D, U, T extends StateParam<D, U>> {
    public final long stateInstId;
    public final int stateCfgId;
    public final StateConfig stateCfg;
    public boolean expire;
    public long expireTimeMills;
    public IStateFinishHandle finishHandle;
    public final Map<Integer, behaviour.runtime.ListenerHandle> listeners = new HashMap<>();
    public final long startTime;
    public T stateParam;

    public State(StateBuilder<D, U, T> stateBuilder) {
        if (stateBuilder.getInstId() == 0) {
            throw new IllegalArgumentException();
        }
        if (stateBuilder.getExpireTimeMills() != -1 && stateBuilder.getExpireTimeMills() <= 0) {
            throw new IllegalArgumentException();
        }
        this.stateInstId = stateBuilder.getInstId();
        this.expireTimeMills = stateBuilder.getExpireTimeMills();
        this.expire = expireTimeMills > 0;
        this.stateCfgId = stateBuilder.getStateCfgId();
        this.stateCfg = stateBuilder.getStateConfig();
        this.finishHandle = stateBuilder.getFinishHandle();
        this.startTime = stateBuilder.getStartTime();
        this.stateParam = stateBuilder.getParam();
    }

    public T getStateParam() {
        return stateParam;
    }

    public void setExpireTimeMills(long expireTimeMills) {
        this.expire = expireTimeMills > 0;
        this.expireTimeMills = expireTimeMills;
    }

    public void updateStateParam(U updateStateParam) {
        if (stateParam != null) {
            stateParam.tryUpdate(updateStateParam);
        }
    }

    public boolean updated(GEntity entity) {
        return stateParam.updated(entity, stateCfgId);
    }

    public boolean isEffect(GEntity entity, long nowMills) {
        if (expire && expireTimeMills <= nowMills) {
            return false;
        }
        return stateParam.isEffect(entity, stateCfgId);
    }


}
