package state.param;

import state.StateParam;

/**
 * Created by zyao on 2024/3/4 14:50
 */
public class NormalStateParam extends StateParam<Object, Object> {

    @Override
    public boolean canToProtocolData() {
        return true;
    }

    @Override
    public Object toProtocolData() {
        return null;
    }

    @Override
    public long checkParam(Object bean) {
        return 0;
    }

    @Override
    public void update(Object bean) {

    }
}
