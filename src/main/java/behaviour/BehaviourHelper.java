package behaviour;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public  class BehaviourHelper {

    public static final Logger logger = LogManager.getLogger(BehaviourHelper.class);
    public static boolean isNodeFinished(Statusenum status) {
        return status == Statusenum.BTFAILURE || status == Statusenum.BTSUCCESS;
    }


    public static boolean isStringNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isConditionNull(config.behaviour.Condition cond) {
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
