package behaviour.composite;

import behaviour.*;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.executor.node.composite.CompIntSwitchCaseExecutor;
import behaviour.expression.ExpressionNumber;
import behaviour.node.Node;
import behaviour.node.composite.CompIntSwitchCase;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.FixedStatusActionTask;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.impl.node.composite.CompIntSwitchCaseTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompIntSwitchCaseRuntimeTest {
    @Test
    @DisplayName("匹配到 case 时执行对应子节点")
    void matchingCaseExecutesSelectedChild() {
        BehaviourStack stack = new BehaviourStack();
        TestSwitchTask switchCase = createSwitch(
                20,
                new int[]{10, 20},
                new Statusenum[]{Statusenum.BTFAILURE, Statusenum.BTSUCCESS},
                null);

        Statusenum result = switchCase.update(stack);

        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(Statusenum.BTINVALID, switchCase.getStatus());
        assertEquals(0, switchCase.getValue());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("匹配到第二个 case 时会执行第二个子节点")
    void matchingSecondCaseKeepsSelectedChildRunning() {
        BehaviourStack stack = new BehaviourStack();
        TestSwitchTask switchCase = createSwitch(
                20,
                new int[]{10, 20},
                new Statusenum[]{Statusenum.BTFAILURE, Statusenum.BTRUNNING},
                null);

        Statusenum result = switchCase.update(stack);

        assertEquals(Statusenum.BTRUNNING, result);
        assertEquals(20, switchCase.getValue());
        assertEquals(1, switchCase.getCurRunningCaseNum());
        assertSame(switchCase.getNodeTaskList().get(1), stack.peekRunningNode());
    }

    @Test
    @DisplayName("没有匹配 case 时执行 default 子节点")
    void unmatchedCaseExecutesDefaultChild() {
        BehaviourStack stack = new BehaviourStack();
        TestSwitchTask switchCase = createSwitch(
                99,
                new int[]{10, 20},
                new Statusenum[]{Statusenum.BTFAILURE, Statusenum.BTFAILURE},
                Statusenum.BTSUCCESS);

        Statusenum result = switchCase.update(stack);

        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(Statusenum.BTINVALID, switchCase.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("没有匹配 case 时会让 default 子节点保持运行")
    void unmatchedCaseKeepsDefaultChildRunning() {
        BehaviourStack stack = new BehaviourStack();
        TestSwitchTask switchCase = createSwitch(
                99,
                new int[]{10, 20},
                new Statusenum[]{Statusenum.BTFAILURE, Statusenum.BTFAILURE},
                Statusenum.BTRUNNING);

        Statusenum result = switchCase.update(stack);

        assertEquals(Statusenum.BTRUNNING, result);
        assertEquals(99, switchCase.getValue());
        assertEquals(-1, switchCase.getCurRunningCaseNum());
        assertTrue(switchCase.useDefaultCase());
        assertSame(switchCase.getDefaultCaseTask(), stack.peekRunningNode());
    }

    @Test
    @DisplayName("运行中的 case 子节点会保留当前 case")
    void runningCaseKeepsSelectedChildRunning() {
        BehaviourStack stack = new BehaviourStack();
        TestSwitchTask switchCase = createSwitch(
                10,
                new int[]{10, 20},
                new Statusenum[]{Statusenum.BTRUNNING, Statusenum.BTSUCCESS},
                Statusenum.BTFAILURE);

        Statusenum result = switchCase.update(stack);

        assertEquals(Statusenum.BTRUNNING, result);
        assertEquals(Statusenum.BTRUNNING, switchCase.getStatus());
        assertEquals(10, switchCase.getValue());
        assertEquals(0, switchCase.getCurRunningCaseNum());
        assertSame(switchCase.getNodeTaskList().get(0), stack.peekRunningNode());
    }

    @Test
    @DisplayName("没有 case 配置时执行 default 子节点")
    void emptyCasesExecuteDefaultChild() {
        BehaviourStack stack = new BehaviourStack();
        TestSwitchTask switchCase = createSwitch(
                10,
                new int[]{},
                new Statusenum[]{},
                Statusenum.BTFAILURE);

        Statusenum result = switchCase.update(stack);

        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(Statusenum.BTINVALID, switchCase.getStatus());
        assertNull(stack.peekRunningNode());
    }

    private TestSwitchTask createSwitch(
            int value,
            int[] cases,
            Statusenum[] caseStatuses,
            Statusenum defaultStatus) {
        TestNumberExpression valueExpression = new TestNumberExpression(TypedNumber.of(value));
        TestSwitch switchCase = new TestSwitch(valueExpression, cases);
        switchCase.bindExecutor(new CompIntSwitchCaseExecutor(switchCase));

        TestBehaviourTreeTask behaviourTreeTask = new TestBehaviourTreeTask();
        TestSwitchTask switchTask = new TestSwitchTask(switchCase, behaviourTreeTask.getContext());
        for (Statusenum caseStatus : caseStatuses) {
            switchTask.getNodeTaskList().add(
                    new FixedStatusActionTask(
                            caseStatus, switchTask, behaviourTreeTask.getContext()));
        }
        if (defaultStatus != null) {
            switchTask.setDefaultCaseTask(
                    new FixedStatusActionTask(
                            defaultStatus, switchTask, behaviourTreeTask.getContext()));
        }
        return switchTask;
    }

    private static final class TestNumberExpression
            extends ExpressionNumber<config.behaviour.expnumber.ConstInteger> {
        private final TypedNumber value;

        private TestNumberExpression(TypedNumber value) {
            super(new config.behaviour.expnumber.ConstInteger(0));
            this.value = value;
            bindExecutor(new TestNumberExpressionExecutor(this));
        }

        @Override
        protected String getLogHead(BehaviourContext context) {
            return "";
        }

        @Override
        public void loadFromCfg() {
        }
    }

    private static final class TestNumberExpressionExecutor
            extends ExpressionExecutor<TestNumberExpression, TypedNumber> {
        private TestNumberExpressionExecutor(TestNumberExpression atom) {
            super(atom);
        }

        @Override
        public TypedNumber calculate(BehaviourContext context) {
            return getAtom().value;
        }

        @Override
        public TypedNumber calculateAndListenEvent(ExpressionRuntime<TypedNumber> runtime) {
            return getAtom().value;
        }
    }

    private static final class TestSwitch extends CompIntSwitchCase {
        private final ExpressionNumber<?> valueExpression;
        private final int[] cases;

        private TestSwitch(ExpressionNumber<?> valueExpression, int[] cases) {
            super(new config.behaviour.node.CompIntSwitchCase(
                    new config.behaviour.condition.NULL(),
                    new config.behaviour.expnumber.ConstInteger(0),
                    new config.behaviour.node.ActReturnStatus(Statusenum.BTFAILURE.getId(), "测试"),
                    java.util.List.of(),
                    java.util.List.of(),
                    java.util.List.of(),
                    "测试"));
            this.valueExpression = valueExpression;
            this.cases = cases;
        }

        @Override
        public ExpressionNumber<?> getCaseValueExpression() {
            return valueExpression;
        }

        @Override
        public int[] getCases() {
            return cases;
        }

        @Override
        public Node<?> getDefaultCase() {
            return null;
        }
    }

    private static final class TestSwitchTask extends CompIntSwitchCaseTask {
        private TestSwitchTask(CompIntSwitchCase atom, BehaviourContext context) {
            super(atom, null, context);
        }

        @Override
        public void log(Level level, String message) {
        }
    }

    private static final class TestBehaviourTreeTask extends BehaviourTreeTask {
        private TestBehaviourTreeTask() {
            super(null, null, null);
        }

        @Override
        public boolean canPrintLog() {
            return false;
        }
    }
}
