package behaviour.decorator;

import behaviour.*;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import behaviour.condition.Condition;
import behaviour.executor.condition.ConditionExecutor;
import behaviour.executor.node.decorator.DecLoopUtilExecutor;
import behaviour.node.decorator.DecLoopUtil;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.FixedStatusActionTask;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.impl.node.decorator.DecLoopUtilTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class DecLoopUtilRuntimeTest {
    @Test
    @DisplayName("条件从 false 变为 true 时装饰器在下一次更新后结束")
    void conditionChangeStopsLoopOnNextUpdate() {
        Fixture fixture = createDecorator(false, new boolean[]{false, true}, Statusenum.BTSUCCESS);
        BehaviourStack stack = fixture.treeTask.getBehaviourStack();

        Statusenum firstResult = fixture.decoratorTask.update(stack);

        assertEquals(Statusenum.BTRUNNING, firstResult);
        assertEquals(1, fixture.condition.getCalculateCount());
        assertSame(fixture.decoratorTask, stack.peekRunningNode());

        Statusenum secondResult = fixture.decoratorTask.update(stack);

        assertEquals(Statusenum.BTSUCCESS, secondResult);
        assertEquals(2, fixture.condition.getCalculateCount());
        assertEquals(Statusenum.BTINVALID, fixture.decoratorTask.getStatus());
        assertNull(stack.peekRunningNode());
        assertNull(fixture.decoratorTask.getDecorateNodeStack().peekRunningNode());
    }

    @Test
    @DisplayName("validOnFinish 为 false 时条件成立会中断运行中的子节点")
    void trueConditionInterruptsRunningChildWhenNotFinishOnly() {
        Fixture fixture = createDecorator(false, new boolean[]{true}, Statusenum.BTRUNNING);

        Statusenum result = fixture.decoratorTask.update(fixture.treeTask.getBehaviourStack());

        FixedStatusActionTask child = (FixedStatusActionTask) fixture.decoratorTask.getDecoratedNodeRuntime();
        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(1, fixture.condition.getCalculateCount());
        assertEquals(1, child.getInterruptCount());
        assertEquals(Statusenum.BTINVALID, fixture.decoratorTask.getStatus());
        assertNull(fixture.treeTask.getBehaviourStack().peekRunningNode());
        assertNull(fixture.decoratorTask.getDecorateNodeStack().peekRunningNode());
    }

    @Test
    @DisplayName("validOnFinish 为 true 时子节点运行中不会提前判断条件")
    void finishOnlyDefersConditionWhileChildIsRunning() {
        Fixture fixture = createDecorator(true, new boolean[]{true}, Statusenum.BTRUNNING);
        BehaviourStack stack = fixture.treeTask.getBehaviourStack();

        Statusenum result = fixture.decoratorTask.update(stack);

        assertEquals(Statusenum.BTRUNNING, result);
        assertEquals(0, fixture.condition.getCalculateCount());
        assertSame(fixture.decoratorTask, stack.peekRunningNode());
        assertSame(fixture.decoratorTask.getDecoratedNodeRuntime(),
                fixture.decoratorTask.getDecorateNodeStack().peekRunningNode());
    }

    @Test
    @DisplayName("validOnFinish 为 true 时子节点完成后才根据条件决定是否结束")
    void finishOnlyChecksConditionAfterChildCompletes() {
        Fixture fixture = createDecorator(true, new boolean[]{false, true}, Statusenum.BTSUCCESS);
        BehaviourStack stack = fixture.treeTask.getBehaviourStack();

        Statusenum firstResult = fixture.decoratorTask.update(stack);

        assertEquals(Statusenum.BTRUNNING, firstResult);
        assertEquals(1, fixture.condition.getCalculateCount());
        assertSame(fixture.decoratorTask, stack.peekRunningNode());

        Statusenum secondResult = fixture.decoratorTask.update(stack);

        assertEquals(Statusenum.BTSUCCESS, secondResult);
        assertEquals(2, fixture.condition.getCalculateCount());
        assertNull(stack.peekRunningNode());
        assertNull(fixture.decoratorTask.getDecorateNodeStack().peekRunningNode());
    }

    private Fixture createDecorator(
            boolean validOnFinish,
            boolean[] conditionResults,
            Statusenum childStatus) {
        TestCondition condition = new TestCondition(conditionResults);
        TestDecLoopUtil decorator = new TestDecLoopUtil(condition, validOnFinish);
        decorator.bindExecutor(new DecLoopUtilExecutor(decorator));

        TestBehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        TestDecLoopUtilTask decoratorTask = new TestDecLoopUtilTask(
                decorator, treeTask.getContext());

        decoratorTask.setDecoratedNodeRuntime(
                new FixedStatusActionTask(childStatus, decoratorTask, treeTask.getContext()));
        return new Fixture(treeTask, condition, decoratorTask);
    }

    private record Fixture(
            TestBehaviourTreeTask treeTask,
            TestCondition condition,
            TestDecLoopUtilTask decoratorTask) {
    }

    private static final class TestCondition extends Condition<config.behaviour.condition.Success> {
        private final boolean[] results;
        private int calculateCount;

        private TestCondition(boolean[] results) {
            super(new config.behaviour.condition.Success());
            this.results = results;
            bindExecutor(new TestConditionExecutor(this));
        }

        private boolean nextResult() {
            int index = Math.min(calculateCount, results.length - 1);
            calculateCount++;
            return results[index];
        }

        private int getCalculateCount() {
            return calculateCount;
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
            return getAtom().nextResult();
        }

        @Override
        public boolean calculateAndListenEvent(ConditionRuntime runtime) {
            return getAtom().nextResult();
        }
    }

    private static final class TestDecLoopUtil extends DecLoopUtil {
        private final Condition<? extends config.behaviour.condition.Condition> condition;

        private TestDecLoopUtil(TestCondition condition, boolean validOnFinish) {
            super(new config.behaviour.node.DecLoopUtil(
                    validOnFinish,
                    new config.behaviour.condition.Success(),
                    new config.behaviour.node.ActReturnStatus(Statusenum.BTSUCCESS.getId(), "测试"),
                    "测试"));
            this.condition = condition;
        }

        @Override
        public Condition<? extends config.behaviour.condition.Condition> getCond() {
            return condition;
        }
    }

    private static final class TestDecLoopUtilTask extends DecLoopUtilTask {
        private TestDecLoopUtilTask(DecLoopUtil atom, BehaviourContext context) {
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
