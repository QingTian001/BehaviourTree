package behaviour.composite;

import behaviour.*;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import behaviour.condition.Condition;
import behaviour.executor.condition.ConditionExecutor;
import behaviour.executor.node.composite.CompCondWeightSelectorExecutor;
import behaviour.node.composite.CompCondWeightSelector;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.FixedStatusActionTask;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.impl.node.composite.CompCondWeightSelectorTask;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class CompCondWeightSelectorRuntimeTest {
    @ParameterizedTest(name = "firstCondition={0}, secondCondition={1}")
    @MethodSource("selectionCases")
    @DisplayName("只有条件成立的权重节点才会参与选择")
    void onlyTrueConditionsParticipateInSelection(
            boolean firstCondition,
            boolean secondCondition,
            Statusenum firstChildStatus,
            Statusenum secondChildStatus) {
        Fixture fixture = createSelector(
                firstCondition,
                secondCondition,
                firstChildStatus,
                secondChildStatus);

        Statusenum result = fixture.selectorTask.update(fixture.treeTask.getBehaviourStack());

        assertEquals(Statusenum.BTSUCCESS, result);
    }

    @Test
    @DisplayName("没有条件成立时选择器返回失败并清理运行栈")
    void noTrueConditionReturnsFailure() {
        Fixture fixture = createSelector(
                false,
                false,
                Statusenum.BTSUCCESS,
                Statusenum.BTSUCCESS);

        Statusenum result = fixture.selectorTask.update(fixture.treeTask.getBehaviourStack());

        FixedStatusActionTask firstChild = (FixedStatusActionTask) fixture.selectorTask.getNodeTaskList().get(0);
        FixedStatusActionTask secondChild = (FixedStatusActionTask) fixture.selectorTask.getNodeTaskList().get(1);
        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(0, firstChild.getUpdateCount());
        assertEquals(0, secondChild.getUpdateCount());
        assertEquals(Statusenum.BTINVALID, fixture.selectorTask.getStatus());
        assertNull(fixture.treeTask.getBehaviourStack().peekRunningNode());
    }

    @Test
    @DisplayName("条件成立且子节点运行中时保留原始节点索引")
    void trueConditionWithRunningChildKeepsOriginalIndex() {
        Fixture fixture = createSelector(
                false,
                true,
                Statusenum.BTSUCCESS,
                Statusenum.BTRUNNING);

        Statusenum result = fixture.selectorTask.update(fixture.treeTask.getBehaviourStack());

        assertEquals(Statusenum.BTRUNNING, result);
        assertEquals(1, fixture.selectorTask.getCurRunningIndex());
        assertSame(fixture.selectorTask.getNodeTaskList().get(1),
                fixture.treeTask.getBehaviourStack().peekRunningNode());
    }

    private static Stream<Arguments> selectionCases() {
        return Stream.of(
                Arguments.of(false, true, Statusenum.BTFAILURE, Statusenum.BTSUCCESS),
                Arguments.of(true, false, Statusenum.BTSUCCESS, Statusenum.BTFAILURE)
        );
    }

    private Fixture createSelector(
            boolean firstCondition,
            boolean secondCondition,
            Statusenum firstChildStatus,
            Statusenum secondChildStatus) {
        TestBehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        TestCondWeightSelector selector = new TestCondWeightSelector(
                new TestCondition(firstCondition),
                new TestCondition(secondCondition));
        selector.bindExecutor(new CompCondWeightSelectorExecutor(selector));

        TestCondWeightSelectorTask selectorTask = new TestCondWeightSelectorTask(
                selector, treeTask.getContext());
        selectorTask.getNodeTaskList().add(createChild(
                firstChildStatus, selectorTask, treeTask.getContext()));
        selectorTask.getNodeTaskList().add(createChild(
                secondChildStatus, selectorTask, treeTask.getContext()));
        return new Fixture(treeTask, selectorTask);
    }

    private FixedStatusActionTask createChild(
            Statusenum status,
            TestCondWeightSelectorTask parent,
            BehaviourContext context) {
        return new FixedStatusActionTask(status, parent, context);
    }

    private record Fixture(
            TestBehaviourTreeTask treeTask,
            TestCondWeightSelectorTask selectorTask) {
    }

    private static final class TestCondition extends Condition<config.behaviour.condition.Success> {
        private final boolean result;

        private TestCondition(boolean result) {
            super(new config.behaviour.condition.Success());
            this.result = result;
            bindExecutor(new TestConditionExecutor(this));
        }

        @Override
        protected String getLogHead(BehaviourContext context) {
            return "";
        }

        @Override
        public void loadFromCfg() {
        }
    }

    private static final class TestConditionExecutor extends ConditionExecutor<TestCondition> {
        private TestConditionExecutor(TestCondition atom) {
            super(atom);
        }

        @Override
        public boolean calculate(BehaviourContext context) {
            return getAtom().result;
        }

        @Override
        public boolean calculateAndListenEvent(ConditionRuntime runtime) {
            return getAtom().result;
        }
    }

    private static final class TestCondWeightSelector extends CompCondWeightSelector {
        private final int[] weights = {5, 5};
        private final Condition<?>[] weightConds;

        private TestCondWeightSelector(Condition<?> firstCondition, Condition<?> secondCondition) {
            super(new config.behaviour.node.CompCondWeightSelector(
                    new config.behaviour.condition.NULL(),
                    List.of(),
                    List.of(),
                    List.of(),
                    "测试"));
            this.weightConds = new Condition<?>[]{firstCondition, secondCondition};
        }

        @Override
        public int[] getWeights() {
            return weights;
        }

        @Override
        public Condition<?>[] getWeightConds() {
            return weightConds;
        }
    }

    private static final class TestCondWeightSelectorTask extends CompCondWeightSelectorTask {
        private TestCondWeightSelectorTask(CompCondWeightSelector atom, BehaviourContext context) {
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

        @Override
        public void log(Level level, Class<?> type, String message, Object... args) {
        }
    }
}
