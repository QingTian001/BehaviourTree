package behaviour.composite;

import behaviour.*;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import behaviour.condition.Condition;
import behaviour.condition.Failure;
import behaviour.executor.condition.FailureExecutor;
import behaviour.executor.node.composite.CompSequenceExecutor;
import behaviour.node.composite.CompSequence;
import behaviour.runtime.FixedStatusActionTask;
import behaviour.task.ConditionTask;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.impl.node.composite.CompSequenceTask;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class CompSequenceRuntimeTest {
    @Test
    @DisplayName("没有子节点的 Sequence 返回成功")
    void emptySequenceReturnsSuccess() {
        BehaviourStack stack = new BehaviourStack();
        TestSequenceTask sequence = createSequence();

        Statusenum result = sequence.update(stack);

        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(Statusenum.BTINVALID, sequence.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("所有子节点成功时 Sequence 返回成功")
    void sequenceReturnsSuccessWhenAllChildrenSucceed() {
        BehaviourStack stack = new BehaviourStack();
        TestSequenceTask sequence = createSequence(Statusenum.BTSUCCESS, Statusenum.BTSUCCESS);

        Statusenum result = sequence.update(stack);

        FixedStatusActionTask firstChild = (FixedStatusActionTask) sequence.getNodeTaskList().get(0);
        FixedStatusActionTask secondChild = (FixedStatusActionTask) sequence.getNodeTaskList().get(1);
        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(1, firstChild.getUpdateCount());
        assertEquals(1, secondChild.getUpdateCount());
        assertEquals(Statusenum.BTINVALID, sequence.getStatus());
        assertEquals(0, sequence.getCurRunningIndex());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("任一子节点失败时 Sequence 返回失败")
    void sequenceReturnsFailureWhenChildFails() {
        BehaviourStack stack = new BehaviourStack();
        TestSequenceTask sequence = createSequence(Statusenum.BTSUCCESS, Statusenum.BTFAILURE);

        Statusenum result = sequence.update(stack);

        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(Statusenum.BTINVALID, sequence.getStatus());
        assertEquals(0, sequence.getCurRunningIndex());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("第一个子节点失败后 Sequence 不会执行后续子节点")
    void failedChildStopsLaterChildren() {
        BehaviourStack stack = new BehaviourStack();
        TestSequenceTask sequence = createSequence(Statusenum.BTFAILURE, Statusenum.BTSUCCESS);

        Statusenum result = sequence.update(stack);

        FixedStatusActionTask firstChild = (FixedStatusActionTask) sequence.getNodeTaskList().get(0);
        FixedStatusActionTask secondChild = (FixedStatusActionTask) sequence.getNodeTaskList().get(1);
        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(1, firstChild.getUpdateCount());
        assertEquals(0, secondChild.getUpdateCount());
    }

    @Test
    @DisplayName("前置条件失败时 Sequence 不会执行子节点")
    void failedPreconditionStopsChildren() {
        BehaviourStack stack = new BehaviourStack();
        TestSequenceTask sequence = createSequence(Statusenum.BTSUCCESS);
        TestConditionTask precondition = createFailingPrecondition(sequence.getContext());
        FixedStatusActionTask cleanAction = new FixedStatusActionTask(
                Statusenum.BTSUCCESS, sequence, sequence.getContext());
        sequence.getCleanActionTaskList().add(cleanAction);
        sequence.setPreCondTask(precondition);

        Statusenum result = sequence.update(stack);

        FixedStatusActionTask child = (FixedStatusActionTask) sequence.getNodeTaskList().get(0);
        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(0, child.getUpdateCount());
        assertEquals(1, cleanAction.getUpdateCount());
        assertEquals(Statusenum.BTINVALID, sequence.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("Sequence 完成时会执行进入动作和清理动作")
    void lifecycleActionsRunAtExpectedBoundaries() {
        config.behaviour.node.CompSequence sequenceConfig = new config.behaviour.node.CompSequence(
                new config.behaviour.condition.NULL(),
                List.of(),
                List.of(),
                List.of(),
                "测试");
        CompSequence sequence = new CompSequence(sequenceConfig);
        sequence.bindExecutor(new CompSequenceExecutor(sequence));

        TestBehaviourTreeTask behaviourTreeTask = new TestBehaviourTreeTask();
        TestSequenceTask sequenceTask = new TestSequenceTask(
                sequence, behaviourTreeTask.getContext());
        FixedStatusActionTask enterAction = new FixedStatusActionTask(
                Statusenum.BTSUCCESS, sequenceTask, behaviourTreeTask.getContext());
        FixedStatusActionTask cleanAction = new FixedStatusActionTask(
                Statusenum.BTSUCCESS, sequenceTask, behaviourTreeTask.getContext());
        FixedStatusActionTask child = new FixedStatusActionTask(
                Statusenum.BTSUCCESS, sequenceTask, behaviourTreeTask.getContext());
        sequenceTask.getEnterActionTaskList().add(enterAction);
        sequenceTask.getCleanActionTaskList().add(cleanAction);
        sequenceTask.getNodeTaskList().add(child);

        Statusenum result = sequenceTask.update(new BehaviourStack());

        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(1, enterAction.getUpdateCount());
        assertEquals(1, child.getUpdateCount());
        assertEquals(1, cleanAction.getUpdateCount());
    }

    @Test
    @DisplayName("Sequence 失败时也会执行清理动作")
    void cleanActionRunsWhenChildFails() {
        config.behaviour.node.CompSequence sequenceConfig = new config.behaviour.node.CompSequence(
                new config.behaviour.condition.NULL(),
                List.of(),
                List.of(),
                List.of(),
                "测试");
        CompSequence sequence = new CompSequence(sequenceConfig);
        sequence.bindExecutor(new CompSequenceExecutor(sequence));

        TestBehaviourTreeTask behaviourTreeTask = new TestBehaviourTreeTask();
        TestSequenceTask sequenceTask = new TestSequenceTask(
                sequence, behaviourTreeTask.getContext());
        FixedStatusActionTask cleanAction = new FixedStatusActionTask(
                Statusenum.BTSUCCESS, sequenceTask, behaviourTreeTask.getContext());
        FixedStatusActionTask child = new FixedStatusActionTask(
                Statusenum.BTFAILURE, sequenceTask, behaviourTreeTask.getContext());
        sequenceTask.getCleanActionTaskList().add(cleanAction);
        sequenceTask.getNodeTaskList().add(child);

        Statusenum result = sequenceTask.update(new BehaviourStack());

        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(1, child.getUpdateCount());
        assertEquals(1, cleanAction.getUpdateCount());
    }

    @Test
    @DisplayName("运行中的子节点会让 Sequence 保持运行并保留在栈中")
    void runningChildKeepsSequenceRunning() {
        BehaviourStack stack = new BehaviourStack();
        TestSequenceTask sequence = createSequence(Statusenum.BTRUNNING, Statusenum.BTSUCCESS);

        Statusenum result = sequence.update(stack);

        assertEquals(Statusenum.BTRUNNING, result);
        assertEquals(Statusenum.BTRUNNING, sequence.getStatus());
        assertEquals(0, sequence.getCurRunningIndex());
        assertSame(sequence.getNodeTaskList().get(0), stack.peekRunningNode());
    }

    private TestSequenceTask createSequence(Statusenum... childStatuses) {
        config.behaviour.node.CompSequence sequenceConfig = new config.behaviour.node.CompSequence(
                new config.behaviour.condition.NULL(),
                List.of(),
                List.of(),
                List.of(),
                "测试");
        CompSequence sequence = new CompSequence(sequenceConfig);
        sequence.bindExecutor(new CompSequenceExecutor(sequence));

        TestBehaviourTreeTask behaviourTreeTask = new TestBehaviourTreeTask();
        TestSequenceTask sequenceTask = new TestSequenceTask(sequence, behaviourTreeTask.getContext());
        for (Statusenum childStatus : childStatuses) {
            sequenceTask.getNodeTaskList().add(
                    new FixedStatusActionTask(
                            childStatus, sequenceTask, behaviourTreeTask.getContext()));
        }
        return sequenceTask;
    }

    private TestConditionTask createFailingPrecondition(BehaviourContext context) {
        Failure failure = new Failure(new config.behaviour.condition.Failure());
        failure.bindExecutor(new FailureExecutor(failure));
        return new TestConditionTask(failure, context);
    }

    private static final class TestSequenceTask extends CompSequenceTask {
        private TestSequenceTask(CompSequence atom, BehaviourContext context) {
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
