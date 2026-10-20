package behaviour.decorator;

import behaviour.*;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import behaviour.condition.Condition;
import behaviour.executor.condition.ConditionExecutor;
import behaviour.executor.node.decorator.DecConditionExecutor;
import behaviour.node.decorator.DecCondition;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.FixedStatusActionTask;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.ConditionTask;
import behaviour.task.impl.node.decorator.DecConditionTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DecConditionRuntimeTest {
    @Test
    @DisplayName("初始条件失败时 DecCondition 不会启动子节点")
    void failedConditionPreventsChildFromStarting() {
        TestBehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        MutableCondition condition = new MutableCondition(false);
        TestConditionTask conditionTask = new TestConditionTask(condition, treeTask.getContext());
        TestDecConditionTask decorator = createDecorator(conditionTask, Statusenum.BTSUCCESS,
                treeTask.getContext());
        BehaviourStack stack = new BehaviourStack();

        Statusenum result = decorator.update(stack);

        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(Statusenum.BTINVALID, decorator.getStatus());
        assertFalse(decorator.isChildStarted());
        assertEquals(Statusenum.BTINVALID, decorator.getDecoratedNodeRuntime().getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("条件通过后 DecCondition 会把子节点加入父运行栈")
    void passedConditionStartsChildOnParentStack() {
        TestBehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        MutableCondition condition = new MutableCondition(true);
        TestConditionTask conditionTask = new TestConditionTask(condition, treeTask.getContext());
        TestDecConditionTask decorator = createDecorator(conditionTask, Statusenum.BTRUNNING,
                treeTask.getContext());
        BehaviourStack stack = new BehaviourStack();

        Statusenum result = decorator.update(stack);

        assertEquals(Statusenum.BTRUNNING, result);
        assertEquals(Statusenum.BTRUNNING, decorator.getStatus());
        assertTrue(decorator.isChildStarted());
        assertSame(decorator.getDecoratedNodeRuntime(), stack.peekRunningNode());
    }

    @Test
    @DisplayName("dirty 后条件仍通过时受保护子节点继续运行")
    void dirtyConditionThatStillPassesKeepsChildRunning() {
        TestBehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        MutableCondition condition = new MutableCondition(true);
        TestConditionTask conditionTask = new TestConditionTask(condition, treeTask.getContext());
        TestDecConditionTask decorator = createDecorator(conditionTask, Statusenum.BTRUNNING,
                treeTask.getContext());
        BehaviourStack stack = new BehaviourStack();
        assertEquals(Statusenum.BTRUNNING, decorator.update(stack));

        conditionTask.setDirty();
        Statusenum result = stack.updateRunningNode(null);

        assertEquals(Statusenum.BTRUNNING, result);
        assertFalse(conditionTask.isDirty());
        assertSame(decorator.getDecoratedNodeRuntime(), stack.peekRunningNode());
    }

    @Test
    @DisplayName("dirty 后条件失败时 DecCondition 中断并移除子节点")
    void dirtyFailedConditionInterruptsChild() {
        TestBehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        MutableCondition condition = new MutableCondition(true);
        TestConditionTask conditionTask = new TestConditionTask(condition, treeTask.getContext());
        TestDecConditionTask decorator = createDecorator(conditionTask, Statusenum.BTRUNNING,
                treeTask.getContext());
        BehaviourStack stack = new BehaviourStack();
        assertEquals(Statusenum.BTRUNNING, decorator.update(stack));

        condition.setResult(false);
        conditionTask.setDirty();
        Statusenum result = stack.updateRunningNode(null);

        FixedStatusActionTask child = (FixedStatusActionTask) decorator.getDecoratedNodeRuntime();
        assertEquals(Statusenum.BTFAILURE, result);
        assertFalse(conditionTask.isDirty());
        assertFalse(decorator.isChildStarted());
        assertEquals(1, child.getInterruptCount());
        assertNull(stack.peekRunningNode());
    }

    private TestDecConditionTask createDecorator(
            ConditionTask conditionTask,
            Statusenum childStatus,
            BehaviourContext context) {
        DecCondition decorator = new DecCondition(new config.behaviour.node.DecCondition(
                new config.behaviour.condition.Success(),
                new config.behaviour.node.ActReturnStatus(childStatus.getId(), "测试"),
                "测试"));
        decorator.bindExecutor(new DecConditionExecutor(decorator));

        TestDecConditionTask decoratorTask = new TestDecConditionTask(
                decorator, conditionTask, context);
        decoratorTask.setDecoratedNodeRuntime(
                new FixedStatusActionTask(childStatus, decoratorTask, context));
        return decoratorTask;
    }

    private static final class MutableCondition extends Condition<config.behaviour.condition.Success> {
        private boolean result;

        private MutableCondition(boolean result) {
            super(new config.behaviour.condition.Success());
            this.result = result;
            bindExecutor(new MutableConditionExecutor(this));
        }

        private void setResult(boolean result) {
            this.result = result;
        }

        private boolean getResult() {
            return result;
        }

        @Override
        public void loadFromCfg() {
        }
    }

    private static final class MutableConditionExecutor extends ConditionExecutor<MutableCondition> {
        private MutableConditionExecutor(MutableCondition atom) {
            super(atom);
        }

        @Override
        public boolean calculate(BehaviourContext context) {
            return getAtom().getResult();
        }

        @Override
        public boolean calculateAndListenEvent(ConditionRuntime runtime) {
            return getAtom().getResult();
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

    private static final class TestDecConditionTask extends DecConditionTask {
        private final ConditionTask conditionTask;

        private TestDecConditionTask(
                DecCondition atom,
                ConditionTask conditionTask,
                BehaviourContext context) {
            super(atom, null, context);
            this.conditionTask = conditionTask;
        }

        @Override
        public ConditionRuntime getConditionRuntime() {
            return conditionTask;
        }

        @Override
        public ConditionTask getPreCondition() {
            return conditionTask;
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
