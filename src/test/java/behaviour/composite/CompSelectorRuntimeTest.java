package behaviour.composite;

import behaviour.*;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;
import behaviour.condition.Condition;
import behaviour.condition.Failure;
import behaviour.executor.condition.FailureExecutor;
import behaviour.executor.node.composite.CompSelectorExecutor;
import behaviour.node.composite.CompSelector;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.ConditionTask;
import behaviour.task.impl.node.composite.CompSelectorTask;
import behaviour.runtime.FixedStatusActionTask;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class CompSelectorRuntimeTest {
    @Test
    @DisplayName("没有子节点的 Selector 返回失败")
    void emptySelectorReturnsFailure() {
        BehaviourStack stack = new BehaviourStack();
        TestSelectorTask selector = createSelector();

        Statusenum result = selector.update(stack);

        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(Statusenum.BTINVALID, selector.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @ParameterizedTest(name = "子节点 {0} 时 Selector 返回 {1}")
    @MethodSource("singleChildResults")
    @DisplayName("单个子节点的终态会被 Selector 传播")
    void selectorPropagatesSingleChildStatus(Statusenum childStatus, Statusenum expectedStatus) {
        BehaviourStack stack = new BehaviourStack();
        TestSelectorTask selector = createSelector(childStatus);

        Statusenum result = selector.update(stack);

        assertEquals(expectedStatus, result);
        assertEquals(Statusenum.BTINVALID, selector.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("第一个子节点失败时 Selector 会尝试下一个节点")
    void selectorTriesNextChildAfterFailure() {
        BehaviourStack stack = new BehaviourStack();
        TestSelectorTask selector = createSelector(Statusenum.BTFAILURE, Statusenum.BTSUCCESS);

        Statusenum result = selector.update(stack);

        FixedStatusActionTask firstChild = (FixedStatusActionTask) selector.getNodeTaskList().get(0);
        FixedStatusActionTask secondChild = (FixedStatusActionTask) selector.getNodeTaskList().get(1);
        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(1, firstChild.getUpdateCount());
        assertEquals(1, secondChild.getUpdateCount());
        assertEquals(Statusenum.BTINVALID, selector.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("所有子节点失败时 Selector 返回失败")
    void selectorReturnsFailureWhenAllChildrenFail() {
        BehaviourStack stack = new BehaviourStack();
        TestSelectorTask selector = createSelector(Statusenum.BTFAILURE, Statusenum.BTFAILURE);

        Statusenum result = selector.update(stack);

        FixedStatusActionTask firstChild = (FixedStatusActionTask) selector.getNodeTaskList().get(0);
        FixedStatusActionTask secondChild = (FixedStatusActionTask) selector.getNodeTaskList().get(1);
        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(1, firstChild.getUpdateCount());
        assertEquals(1, secondChild.getUpdateCount());
        assertEquals(Statusenum.BTINVALID, selector.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("运行中的子节点会让 Selector 保持运行并保留在栈中")
    void runningChildKeepsSelectorRunning() {
        BehaviourStack stack = new BehaviourStack();
        TestSelectorTask selector = createSelector(Statusenum.BTRUNNING, Statusenum.BTSUCCESS);

        Statusenum result = selector.update(stack);

        assertEquals(Statusenum.BTRUNNING, result);
        assertEquals(Statusenum.BTRUNNING, selector.getStatus());
        assertEquals(0, selector.getCurRunningIndex());
        assertSame(selector.getNodeTaskList().get(0), stack.peekRunningNode());
    }

    @Test
    @DisplayName("前置条件失败时 Selector 不会执行子节点")
    void failedPreconditionStopsChildren() {
        BehaviourStack stack = new BehaviourStack();
        TestSelectorTask selector = createSelector(Statusenum.BTSUCCESS);
        Failure failure = new Failure(new config.behaviour.condition.Failure());
        failure.bindExecutor(new FailureExecutor(failure));
        selector.setPreCondTask(new TestConditionTask(failure, selector.getContext()));

        Statusenum result = selector.update(stack);

        FixedStatusActionTask child = (FixedStatusActionTask) selector.getNodeTaskList().get(0);
        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(0, child.getUpdateCount());
        assertEquals(Statusenum.BTINVALID, selector.getStatus());
        assertNull(stack.peekRunningNode());
    }

    private static Stream<Arguments> singleChildResults() {
        return Stream.of(
                Arguments.of(Statusenum.BTSUCCESS, Statusenum.BTSUCCESS),
                Arguments.of(Statusenum.BTFAILURE, Statusenum.BTFAILURE)
        );
    }

    private TestSelectorTask createSelector(Statusenum... childStatuses) {
        config.behaviour.node.CompSelector selectorConfig = new config.behaviour.node.CompSelector(
                new config.behaviour.condition.NULL(),
                List.of(),
                List.of(),
                List.of(),
                "测试");
        CompSelector selector = new CompSelector(selectorConfig);
        selector.bindExecutor(new CompSelectorExecutor(selector));

        TestBehaviourTreeTask behaviourTreeTask = new TestBehaviourTreeTask();
        TestSelectorTask selectorTask = new TestSelectorTask(selector, behaviourTreeTask.getContext());
        for (Statusenum childStatus : childStatuses) {
            selectorTask.getNodeTaskList().add(
                    new FixedStatusActionTask(
                            childStatus, selectorTask, behaviourTreeTask.getContext()));
        }
        return selectorTask;
    }

    private static final class TestSelectorTask extends CompSelectorTask {
        private TestSelectorTask(CompSelector atom, BehaviourContext context) {
            super(atom, null, context);
        }

        @Override
        public void log(Level level, String message) {
        }
    }

    private static final class TestConditionTask extends ConditionTask {
        private TestConditionTask(Condition<?> atom, BehaviourContext context) {
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
