package behaviour.runtime;

import behaviour.*;

import util.OutObject;
import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import behaviour.condition.Condition;
import behaviour.condition.Failure;
import behaviour.condition.Success;
import behaviour.executor.condition.FailureExecutor;
import behaviour.executor.condition.SuccessExecutor;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.executor.node.composite.CompSequenceExecutor;
import behaviour.node.IPreConditionTask;
import behaviour.node.action.Action;
import behaviour.node.composite.CompSequence;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.BehaviourTask;
import behaviour.task.ConditionTask;
import behaviour.task.NodeTask;
import behaviour.task.ActionTask;
import behaviour.task.impl.node.composite.CompSequenceTask;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BehaviourStackTest {
    @Test
    @DisplayName("节点按后进先出顺序入栈和出栈")
    void nodesArePushedAndPoppedInLifoOrder() {
        BehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        ActionTask first = createActionTask(Statusenum.BTRUNNING, null, treeTask.getContext());
        ActionTask second = createActionTask(Statusenum.BTRUNNING, null, treeTask.getContext());
        BehaviourStack stack = new BehaviourStack();

        stack.pushRunningNode(first);
        stack.pushRunningNode(second);

        assertSame(second, stack.peekRunningNode());
        assertSame(second, stack.popRunningNode());
        assertSame(first, stack.popRunningNode());
        assertSame(null, stack.peekRunningNode());
    }

    @Test
    @DisplayName("子节点完成后 BehaviourStack 会沿父链回溯状态")
    void completedChildBacktracesStatusToParent() {
        TestBehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        CompSequence sequence = new CompSequence(new config.behaviour.node.CompSequence(
                new config.behaviour.condition.NULL(),
                List.of(),
                List.of(),
                List.of(),
                "测试"));
        sequence.bindExecutor(new CompSequenceExecutor(sequence));
        TestSequenceTask sequenceTask = new TestSequenceTask(sequence, treeTask.getContext());
        StatusSequenceActionTask childTask = new StatusSequenceActionTask(
                new Statusenum[]{Statusenum.BTRUNNING, Statusenum.BTSUCCESS},
                sequenceTask,
                treeTask.getContext());
        sequenceTask.getNodeTaskList().add(childTask);
        BehaviourStack stack = new BehaviourStack();

        assertEquals(Statusenum.BTRUNNING, sequenceTask.update(stack));
        assertSame(childTask, stack.peekRunningNode());

        Statusenum result = stack.updateRunningNode(null);

        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(Statusenum.BTINVALID, sequenceTask.getStatus());
        assertEquals(2, childTask.getUpdateCount());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("子节点失败后 BehaviourStack 会把失败状态回溯给父节点")
    void failedChildBacktracesFailureToParent() {
        TestBehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        CompSequence sequence = new CompSequence(new config.behaviour.node.CompSequence(
                new config.behaviour.condition.NULL(),
                List.of(),
                List.of(),
                List.of(),
                "测试"));
        sequence.bindExecutor(new CompSequenceExecutor(sequence));
        TestSequenceTask sequenceTask = new TestSequenceTask(sequence, treeTask.getContext());
        StatusSequenceActionTask childTask = new StatusSequenceActionTask(
                new Statusenum[]{Statusenum.BTRUNNING, Statusenum.BTFAILURE},
                sequenceTask,
                treeTask.getContext());
        sequenceTask.getNodeTaskList().add(childTask);
        BehaviourStack stack = new BehaviourStack();

        assertEquals(Statusenum.BTRUNNING, sequenceTask.update(stack));

        Statusenum result = stack.updateRunningNode(null);

        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(Statusenum.BTINVALID, sequenceTask.getStatus());
        assertEquals(2, childTask.getUpdateCount());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("重置运行栈后栈顶为空")
    void resetClearsRunningNodes() {
        BehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        BehaviourStack stack = new BehaviourStack();
        stack.pushRunningNode(createActionTask(Statusenum.BTRUNNING, null, treeTask.getContext()));

        stack.reset();

        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("批量中断运行节点时遵循后进先出并重置节点")
    void interruptAllRunningNodesUsesLifoOrderAndResetsNodes() {
        BehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        List<String> interruptOrder = new ArrayList<>();
        InterruptTrackingActionTask first = new InterruptTrackingActionTask(
                "first", interruptOrder, treeTask.getContext());
        InterruptTrackingActionTask second = new InterruptTrackingActionTask(
                "second", interruptOrder, treeTask.getContext());
        BehaviourStack stack = new BehaviourStack();
        stack.pushRunningNode(first);
        stack.pushRunningNode(second);

        stack.popAndInterruptRunningNodeAll();

        assertEquals(List.of("second", "first"), interruptOrder);
        assertEquals(Statusenum.BTINVALID, first.getStatus());
        assertEquals(Statusenum.BTINVALID, second.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("dirty 的成功前置条件会被重新计算并清除 dirty 标记")
    void dirtySuccessfulPreconditionIsRecalculatedAndCleared() {
        BehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        TestConditionTask condition = createConditionTask(true, treeTask.getContext());
        TestPreconditionTask node = createPreconditionTask(condition, treeTask.getContext());
        BehaviourStack stack = new BehaviourStack();
        stack.pushRunningNode(node);
        condition.setDirty();
        OutObject<NodeTask> failedNode = new OutObject<>();

        assertTrue(stack.checkStackPreConditions(failedNode));
        assertFalse(condition.isDirty());
        assertNull(failedNode.value);
        assertSame(node, stack.peekRunningNode());
    }

    @Test
    @DisplayName("失败的 dirty 前置条件会定位到对应节点")
    void failedDirtyPreconditionIdentifiesNode() {
        BehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        TestConditionTask condition = createConditionTask(false, treeTask.getContext());
        TestPreconditionTask node = createPreconditionTask(condition, treeTask.getContext());
        BehaviourStack stack = new BehaviourStack();
        stack.pushRunningNode(node);
        condition.setDirty();
        OutObject<NodeTask> failedNode = new OutObject<>();

        assertFalse(stack.checkStackPreConditions(failedNode));
        assertFalse(condition.isDirty());
        assertSame(node, failedNode.value);
    }

    @Test
    @DisplayName("前置条件失败时更新运行栈会中断并移除节点")
    void failedPreconditionInterruptsAndRemovesNode() {
        BehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        TestConditionTask condition = createConditionTask(false, treeTask.getContext());
        TestPreconditionTask node = createPreconditionTask(condition, treeTask.getContext());
        BehaviourStack stack = new BehaviourStack();
        stack.pushRunningNode(node);
        condition.setDirty();

        Statusenum result = stack.updateRunningNode(null);

        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(1, node.getInterruptCount());
        assertNull(stack.peekRunningNode());
        assertFalse(condition.isDirty());
    }

    private FixedStatusActionTask createActionTask(
            Statusenum status,
            BehaviourTask<?> parent,
            BehaviourContext context) {
        return new FixedStatusActionTask(status, parent, context);
    }

    private TestConditionTask createConditionTask(boolean result, BehaviourContext context) {
        Condition<?> condition;
        if (result) {
            Success success = new Success(new config.behaviour.condition.Success());
            success.bindExecutor(new SuccessExecutor(success));
            condition = success;
        } else {
            Failure failure = new Failure(new config.behaviour.condition.Failure());
            failure.bindExecutor(new FailureExecutor(failure));
            condition = failure;
        }
        return new TestConditionTask(condition, context);
    }

    private TestPreconditionTask createPreconditionTask(
            ConditionTask condition,
            BehaviourContext context) {
        return new TestPreconditionTask(condition, context);
    }

    private static final class TestPreconditionTask extends FixedStatusActionTask implements IPreConditionTask {
        private final ConditionTask condition;

        private TestPreconditionTask(
                ConditionTask condition,
                BehaviourContext context) {
            super(Statusenum.BTRUNNING, null, context);
            this.condition = condition;
        }

        @Override
        public ConditionTask getPreCondition() {
            return condition;
        }
    }

    private static final class TestSequenceTask extends CompSequenceTask {
        private TestSequenceTask(CompSequence atom, BehaviourContext context) {
            super(atom, null, context);
        }

        @Override
        public void log(Level level, String message) {
        }
    }

    private static final class StatusSequenceActionTask extends ActionTask {
        private StatusSequenceActionTask(
                Statusenum[] statuses,
                BehaviourTask<?> parent,
                BehaviourContext context) {
            super(new StatusSequenceAction(statuses), parent, context);
        }

        private int getUpdateCount() {
            return ((StatusSequenceAction) getAtom()).getUpdateCount();
        }

        @Override
        public void log(Level level, String message) {
        }
    }

    private static final class StatusSequenceAction
            extends Action<config.behaviour.node.ActReturnStatus> {
        private final Statusenum[] statuses;
        private int updateCount;

        private StatusSequenceAction(Statusenum[] statuses) {
            super(new config.behaviour.node.ActReturnStatus(
                    Statusenum.BTRUNNING.getId(), "测试"));
            this.statuses = statuses;
            bindExecutor(new StatusSequenceActionExecutor(this));
        }

        private int getUpdateCount() {
            return updateCount;
        }

        @Override
        public void loadFromCfg() {
        }
    }

    private static final class StatusSequenceActionExecutor
            extends ActionExecutor<StatusSequenceAction> {
        private StatusSequenceActionExecutor(StatusSequenceAction atom) {
            super(atom);
        }

        @Override
        public boolean onEnter(NodeRuntime runtime) {
            return true;
        }

        @Override
        public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
            int index = Math.min(getAtom().updateCount, getAtom().statuses.length - 1);
            getAtom().updateCount++;
            return getAtom().statuses[index];
        }

        @Override
        public void onInterrupt(NodeRuntime runtime) {
        }

        @Override
        public void reset(NodeRuntime runtime, boolean recursive) {
        }
    }

    private static final class InterruptTrackingActionTask extends ActionTask {
        private InterruptTrackingActionTask(
                String name,
                List<String> interruptOrder,
                BehaviourContext context) {
            super(new InterruptTrackingAction(name, interruptOrder), null, context);
        }

        @Override
        public void log(Level level, String message) {
        }
    }

    private static final class InterruptTrackingAction
            extends Action<config.behaviour.node.ActReturnStatus> {
        private final String name;
        private final List<String> interruptOrder;

        private InterruptTrackingAction(String name, List<String> interruptOrder) {
            super(new config.behaviour.node.ActReturnStatus(
                    Statusenum.BTRUNNING.getId(), "测试"));
            this.name = name;
            this.interruptOrder = interruptOrder;
            bindExecutor(new InterruptTrackingActionExecutor(this));
        }

        @Override
        public void loadFromCfg() {
        }
    }

    private static final class InterruptTrackingActionExecutor
            extends ActionExecutor<InterruptTrackingAction> {
        private InterruptTrackingActionExecutor(InterruptTrackingAction atom) {
            super(atom);
        }

        @Override
        public boolean onEnter(NodeRuntime runtime) {
            return true;
        }

        @Override
        public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
            return Statusenum.BTRUNNING;
        }

        @Override
        public void onInterrupt(NodeRuntime runtime) {
            getAtom().interruptOrder.add(getAtom().name);
        }

        @Override
        public void reset(NodeRuntime runtime, boolean recursive) {
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
