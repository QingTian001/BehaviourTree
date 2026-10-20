package behaviour.composite;

import behaviour.*;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import behaviour.executor.node.composite.CompWeightSelectorExecutor;
import behaviour.node.composite.CompWeightSelector;
import behaviour.runtime.FixedStatusActionTask;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.impl.node.composite.CompWeightSelectorTask;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CompWeightSelectorRuntimeTest {
    @Test
    @DisplayName("零权重节点不会被选中")
    void zeroWeightNodeIsNotSelected() {
        BehaviourStack stack = new BehaviourStack();
        TestWeightSelectorTask selector = createSelector(
                new int[]{0, 5},
                Statusenum.BTFAILURE,
                Statusenum.BTSUCCESS);

        Statusenum result = selector.update(stack);

        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(Statusenum.BTINVALID, selector.getStatus());
        assertEquals(0, selector.getCurRunningIndex());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("第一个节点拥有唯一正权重时会被选中")
    void onlyPositiveWeightNodeIsSelected() {
        BehaviourStack stack = new BehaviourStack();
        TestWeightSelectorTask selector = createSelector(
                new int[]{5, 0},
                Statusenum.BTSUCCESS,
                Statusenum.BTFAILURE);

        Statusenum result = selector.update(stack);

        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(Statusenum.BTINVALID, selector.getStatus());
        assertEquals(0, selector.getCurRunningIndex());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("所有权重为零时抛出非法参数异常")
    void allZeroWeightsThrowException() {
        BehaviourStack stack = new BehaviourStack();
        TestWeightSelectorTask selector = createSelector(
                new int[]{0, 0},
                Statusenum.BTSUCCESS,
                Statusenum.BTFAILURE);

        assertThrows(IllegalArgumentException.class, () -> selector.update(stack));
    }

    private TestWeightSelectorTask createSelector(int[] weights, Statusenum... childStatuses) {
        TestWeightSelector selector = new TestWeightSelector(weights);
        selector.bindExecutor(new CompWeightSelectorExecutor(selector));

        TestBehaviourTreeTask behaviourTreeTask = new TestBehaviourTreeTask();
        TestWeightSelectorTask selectorTask = new TestWeightSelectorTask(
                selector, behaviourTreeTask.getContext());
        for (Statusenum childStatus : childStatuses) {
            selectorTask.getNodeTaskList().add(
                    new FixedStatusActionTask(
                            childStatus, selectorTask, behaviourTreeTask.getContext()));
        }
        return selectorTask;
    }

    private static final class TestWeightSelector extends CompWeightSelector {
        private final int[] weights;

        private TestWeightSelector(int[] weights) {
            super(new config.behaviour.node.CompWeightSelector(
                    new config.behaviour.condition.NULL(),
                    List.of(),
                    List.of(),
                    List.of(),
                    "测试"));
            this.weights = weights;
        }

        @Override
        public int[] getWeights() {
            return weights;
        }
    }

    private static final class TestWeightSelectorTask extends CompWeightSelectorTask {
        private TestWeightSelectorTask(CompWeightSelector atom, BehaviourContext context) {
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
