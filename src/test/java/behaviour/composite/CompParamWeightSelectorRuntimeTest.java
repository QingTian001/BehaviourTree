package behaviour.composite;

import behaviour.*;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import behaviour.executor.expression.number.GetBBNumberExecutor;
import behaviour.executor.node.composite.CompParamWeightSelectorExecutor;
import behaviour.expression.ExpressionNumber;
import behaviour.expression.number.GetBBNumber;
import behaviour.node.composite.CompParamWeightSelector;
import behaviour.runtime.FixedStatusActionTask;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.impl.node.composite.CompParamWeightSelectorTask;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompParamWeightSelectorRuntimeTest {
    @ParameterizedTest(name = "first={0}, second={1}")
    @MethodSource("selectionCases")
    @DisplayName("黑板中的参数决定唯一正权重子节点")
    void blackBoardValuesSelectOnlyPositiveWeightChild(
            int firstWeight,
            int secondWeight,
            Statusenum firstChildStatus,
            Statusenum secondChildStatus) {
        Fixture fixture = createSelector(
                firstWeight,
                secondWeight,
                firstChildStatus,
                secondChildStatus);

        Statusenum result = fixture.selectorTask.update(fixture.treeTask.getBehaviourStack());

        assertEquals(Statusenum.BTSUCCESS, result);
        assertArrayEquals(new int[]{0, 0}, fixture.selectorTask.getWeights());
        assertEquals(0, fixture.selectorTask.getCurRunningIndex());
    }

    @Test
    @DisplayName("被选中的运行中子节点保留选择索引并进入行为栈")
    void selectedRunningChildRemainsOnStack() {
        Fixture fixture = createSelector(0, 5, Statusenum.BTFAILURE, Statusenum.BTRUNNING);

        Statusenum result = fixture.selectorTask.update(fixture.treeTask.getBehaviourStack());

        assertEquals(Statusenum.BTRUNNING, result);
        assertEquals(1, fixture.selectorTask.getCurRunningIndex());
        assertSame(fixture.selectorTask.getNodeTaskList().get(1),
                fixture.treeTask.getBehaviourStack().peekRunningNode());
    }

    @Test
    @DisplayName("缺少权重黑板参数时抛出包含 key 的异常")
    void missingWeightBlackBoardValueThrowsException() {
        Fixture fixture = createSelector(0, 5, Statusenum.BTFAILURE, Statusenum.BTSUCCESS);
        fixture.treeTask.clearBlackBoard();
        fixture.treeTask.getBlackBoard().putValue("firstWeight", 0);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fixture.selectorTask.update(fixture.treeTask.getBehaviourStack()));

        assertTrue(exception.getMessage().contains("secondWeight"));
    }

    private static Stream<Arguments> selectionCases() {
        return Stream.of(
                Arguments.of(0, 5, Statusenum.BTFAILURE, Statusenum.BTSUCCESS),
                Arguments.of(5, 0, Statusenum.BTSUCCESS, Statusenum.BTFAILURE)
        );
    }

    private Fixture createSelector(
            int firstWeight,
            int secondWeight,
            Statusenum firstChildStatus,
            Statusenum secondChildStatus) {
        TestBehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        treeTask.getBlackBoard().putValue("firstWeight", firstWeight);
        treeTask.getBlackBoard().putValue("secondWeight", secondWeight);

        TestParamWeightSelector selector = new TestParamWeightSelector(
                new ExpressionNumber<?>[]{
                        new TestGetBBNumber("firstWeight"),
                        new TestGetBBNumber("secondWeight")
                });
        selector.bindExecutor(new CompParamWeightSelectorExecutor(selector));

        TestParamWeightSelectorTask selectorTask = new TestParamWeightSelectorTask(
                selector, treeTask.getContext());
        selectorTask.newWeights();
        selectorTask.getNodeTaskList().add(createChild(
                firstChildStatus, selectorTask, treeTask.getContext()));
        selectorTask.getNodeTaskList().add(createChild(
                secondChildStatus, selectorTask, treeTask.getContext()));
        return new Fixture(treeTask, selectorTask);
    }

    private FixedStatusActionTask createChild(
            Statusenum status,
            TestParamWeightSelectorTask parent,
            BehaviourContext context) {
        return new FixedStatusActionTask(status, parent, context);
    }

    private record Fixture(
            TestBehaviourTreeTask treeTask,
            TestParamWeightSelectorTask selectorTask) {
    }

    private static final class TestGetBBNumber extends GetBBNumber {
        private TestGetBBNumber(String bbKey) {
            super(new config.behaviour.expnumber.GetBBNumber(bbKey));
            bindExecutor(new GetBBNumberExecutor(this));
        }

        @Override
        protected String getLogHead(BehaviourContext context) {
            return "";
        }
    }

    private static final class TestParamWeightSelector extends CompParamWeightSelector {
        private final ExpressionNumber<?>[] weightExps;

        private TestParamWeightSelector(ExpressionNumber<?>[] weightExps) {
            super(new config.behaviour.node.CompParamWeightSelector(
                    new config.behaviour.condition.NULL(),
                    List.of(),
                    List.of(),
                    List.of(),
                    "测试"));
            this.weightExps = weightExps;
        }

        @Override
        public ExpressionNumber<?>[] getWeightExps() {
            return weightExps;
        }
    }

    private static final class TestParamWeightSelectorTask extends CompParamWeightSelectorTask {
        private TestParamWeightSelectorTask(CompParamWeightSelector atom, BehaviourContext context) {
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
