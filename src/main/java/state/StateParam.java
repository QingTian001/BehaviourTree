package state;

import behaviour.GEntity;

/**
 * Created by zyao on 2022/10/17 21:09
 */
public abstract class StateParam<D, U> {

    public abstract D toProtocolData();

    public abstract long checkParam(U bean);

    public abstract void update(U bean);

    public boolean canToProtocolData() {
        return true;
    }

    public long tryUpdate(U bean) {
        long ret = checkParam(bean);
        if (ret != 0) {
            return ret;
        }
        update(bean);
        return 0;
    }


    public boolean updated(GEntity entity, int stateId) {
       return false;
    }

    public boolean isEffect(GEntity entity, int stateId) {
        return true;
    }
}
