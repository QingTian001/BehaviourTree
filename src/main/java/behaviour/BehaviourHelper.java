package behaviour;

import config.behaviour.Statusenum;
import java.util.*;

/**
 * Created by zyao on 2023/12/20 11:45
 */
public  class BehaviourHelper {
    public static boolean isNodeFinished(Statusenum status) {
        return status == Statusenum.BTFAILURE || status == Statusenum.BTSUCCESS;
    }

    public static boolean isStringNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isConditionNull(config.behaviour.condition.Condition cond) {
        return cond.getClass() == config.behaviour.condition.NULL.class;
    }

    public static double convertToDouble(int value) {
        return value;
    }

    public static double convertToDouble(long value) {
        return value;
    }

    public static double convertToDouble(float value) {
        return value;
    }

}
