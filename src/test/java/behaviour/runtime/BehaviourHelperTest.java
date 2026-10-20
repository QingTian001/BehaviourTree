package behaviour.runtime;

import behaviour.*;

import config.behaviour.Statusenum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BehaviourHelperTest {
    @Test
    @DisplayName("只有成功和失败状态表示节点已结束")
    void onlySuccessAndFailureAreFinished() {
        assertTrue(BehaviourHelper.isNodeFinished(Statusenum.BTSUCCESS));
        assertTrue(BehaviourHelper.isNodeFinished(Statusenum.BTFAILURE));
        assertFalse(BehaviourHelper.isNodeFinished(Statusenum.BTRUNNING));
        assertFalse(BehaviourHelper.isNodeFinished(Statusenum.BTINVALID));
    }

    @Test
    @DisplayName("字符串 null 和空字符串被识别为空")
    void nullAndEmptyStringAreEmpty() {
        assertTrue(BehaviourHelper.isStringNullOrEmpty(null));
        assertTrue(BehaviourHelper.isStringNullOrEmpty(""));
        assertFalse(BehaviourHelper.isStringNullOrEmpty("value"));
    }

    @Test
    @DisplayName("不同数值类型都可以转换为 double")
    void numberValuesConvertToDouble() {
        assertAll(
                () -> assertEquals(1.0, BehaviourHelper.convertToDouble(1), 0.0),
                () -> assertEquals(2.0, BehaviourHelper.convertToDouble(2L), 0.0),
                () -> assertEquals(3.5, BehaviourHelper.convertToDouble(3.5f), 0.000001)
        );
    }

    @Test
    @DisplayName("NULL 条件配置可以被识别")
    void nullConditionConfigIsRecognized() {
        assertTrue(BehaviourHelper.isConditionNull(new config.behaviour.condition.NULL()));
        assertFalse(BehaviourHelper.isConditionNull(new config.behaviour.condition.Success()));
    }
}
