package behaviour.composite;

import behaviour.*;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.executor.node.composite.CompParallelExecutor;
import behaviour.node.action.Action;
import behaviour.node.composite.CompParallel;
import behaviour.runtime.FixedStatusActionTask;
import behaviour.runtime.NodeRuntime;
import behaviour.task.ActionTask;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.BehaviourTask;
import behaviour.task.impl.node.composite.CompParallelTask;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class CompParallelRuntimeTest {
    @Test
    @DisplayName("没有子节点的 Parallel 返回成功")
    void emptyParallelReturnsSuccess() {
        BehaviourStack stack = new BehaviourStack();
        TestParallelTask parallel = createParallel(false, false);

        Statusenum result = parallel.update(stack);

        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(Statusenum.BTINVALID, parallel.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("默认策略下所有子节点完成后按成功数量汇总")
    void defaultStrategyAggregatesAllFinishedChildren() {
        BehaviourStack stack = new BehaviourStack();
        TestParallelTask parallel = createParallel(
                false, false, Statusenum.BTSUCCESS, Statusenum.BTFAILURE);

        Statusenum result = parallel.update(stack);

        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(Statusenum.BTINVALID, parallel.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("默认策略下所有子节点成功时 Parallel 返回成功")
    void defaultStrategyReturnsSuccessWhenAllChildrenSucceed() {
        BehaviourStack stack = new BehaviourStack();
        TestParallelTask parallel = createParallel(
                false, false, Statusenum.BTSUCCESS, Statusenum.BTSUCCESS);

        Statusenum result = parallel.update(stack);

        FixedStatusActionTask firstChild = (FixedStatusActionTask) parallel.getNodeTaskList().get(0);
        FixedStatusActionTask secondChild = (FixedStatusActionTask) parallel.getNodeTaskList().get(1);
        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(1, firstChild.getUpdateCount());
        assertEquals(1, secondChild.getUpdateCount());
        assertEquals(Statusenum.BTINVALID, parallel.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("存在运行中子节点时 Parallel 保持运行并保留各自子栈")
    void runningChildKeepsParallelRunning() {
        BehaviourStack stack = new BehaviourStack();
        TestParallelTask parallel = createParallel(
                false, false, Statusenum.BTRUNNING, Statusenum.BTSUCCESS);

        Statusenum result = parallel.update(stack);

        assertEquals(Statusenum.BTRUNNING, result);
        assertEquals(Statusenum.BTRUNNING, parallel.getStatus());
        assertSame(parallel, stack.peekRunningNode());
        assertSame(parallel.getNodeTaskList().get(0),
                parallel.getBehaviourStackList().get(0).peekRunningNode());
        assertEquals(1, parallel.getFinishedIndexes().size());
        assertEquals(1, parallel.getSuccessNodeNum());
    }

    @Test
    @DisplayName("重置运行中的 Parallel 会清理统计状态和子栈")
    void resetClearsRunningChildrenAndStatistics() {
        BehaviourStack stack = new BehaviourStack();
        TestParallelTask parallel = createParallel(
                false, false, Statusenum.BTRUNNING, Statusenum.BTRUNNING);

        assertEquals(Statusenum.BTRUNNING, parallel.update(stack));

        parallel.reset(false);

        assertEquals(Statusenum.BTINVALID, parallel.getStatus());
        assertEquals(0, parallel.getFinishedIndexes().size());
        assertEquals(0, parallel.getSuccessNodeNum());
        assertEquals(Statusenum.BTINVALID, parallel.getNodeTaskList().get(0).getStatus());
        assertEquals(Statusenum.BTINVALID, parallel.getNodeTaskList().get(1).getStatus());
        assertNull(parallel.getBehaviourStackList().get(0).peekRunningNode());
        assertNull(parallel.getBehaviourStackList().get(1).peekRunningNode());
    }

    @Test
    @DisplayName("oneSuccAllSucc 为 true 时一个成功节点即可结束 Parallel")
    void oneSuccessCanFinishParallel() {
        BehaviourStack stack = new BehaviourStack();
        TestParallelTask parallel = createParallel(
                true, false, Statusenum.BTRUNNING, Statusenum.BTSUCCESS);

        Statusenum result = parallel.update(stack);

        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(Statusenum.BTINVALID, parallel.getStatus());
        assertNull(stack.peekRunningNode());
        assertNull(parallel.getBehaviourStackList().get(0).peekRunningNode());
    }

    @Test
    @DisplayName("oneFailAllFail 为 true 时一个失败节点即可结束 Parallel")
    void oneFailureCanFinishParallel() {
        BehaviourStack stack = new BehaviourStack();
        TestParallelTask parallel = createParallel(
                false, true, Statusenum.BTFAILURE, Statusenum.BTRUNNING);

        Statusenum result = parallel.update(stack);

        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(Statusenum.BTINVALID, parallel.getStatus());
        assertNull(stack.peekRunningNode());
        assertNull(parallel.getBehaviourStackList().get(1).peekRunningNode());
    }

    @Test
    @DisplayName("oneFailAllFail 会中断已经运行的子节点")
    void oneFailureInterruptsRunningChild() {
        CompParallel parallel = new CompParallel(new config.behaviour.node.CompParallel(
                false,
                true,
                List.of(),
                List.of(),
                List.of(),
                "测试"));
        parallel.bindExecutor(new CompParallelExecutor(parallel));

        TestBehaviourTreeTask behaviourTreeTask = new TestBehaviourTreeTask();
        TestParallelTask parallelTask = new TestParallelTask(parallel, behaviourTreeTask.getContext());
        InterruptTrackingActionTask runningTask = new InterruptTrackingActionTask(
                Statusenum.BTRUNNING, parallelTask, behaviourTreeTask.getContext());
        parallelTask.getNodeTaskList().add(runningTask);
        parallelTask.getNodeTaskList().add(new FixedStatusActionTask(
                Statusenum.BTFAILURE, parallelTask, behaviourTreeTask.getContext()));
        parallelTask.getBehaviourStackList().add(new BehaviourStack());
        parallelTask.getBehaviourStackList().add(new BehaviourStack());

        Statusenum result = parallelTask.update(new BehaviourStack());

        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(1, runningTask.getInterruptCount());
    }

    @Test
    @DisplayName("oneSuccAllSucc 会中断已经运行的子节点")
    void oneSuccessInterruptsRunningChild() {
        CompParallel parallel = new CompParallel(new config.behaviour.node.CompParallel(
                true,
                false,
                List.of(),
                List.of(),
                List.of(),
                "测试"));
        parallel.bindExecutor(new CompParallelExecutor(parallel));

        TestBehaviourTreeTask behaviourTreeTask = new TestBehaviourTreeTask();
        TestParallelTask parallelTask = new TestParallelTask(parallel, behaviourTreeTask.getContext());
        InterruptTrackingActionTask runningTask = new InterruptTrackingActionTask(
                Statusenum.BTRUNNING, parallelTask, behaviourTreeTask.getContext());
        parallelTask.getNodeTaskList().add(runningTask);
        parallelTask.getNodeTaskList().add(new FixedStatusActionTask(
                Statusenum.BTSUCCESS, parallelTask, behaviourTreeTask.getContext()));
        parallelTask.getBehaviourStackList().add(new BehaviourStack());
        parallelTask.getBehaviourStackList().add(new BehaviourStack());

        Statusenum result = parallelTask.update(new BehaviourStack());

        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(1, runningTask.getInterruptCount());
    }

    private TestParallelTask createParallel(boolean oneSuccess, boolean oneFailure, Statusenum... childStatuses) {
        CompParallel parallel = new CompParallel(new config.behaviour.node.CompParallel(
                oneSuccess,
                oneFailure,
                List.of(),
                List.of(),
                List.of(),
                "测试"));
        parallel.bindExecutor(new CompParallelExecutor(parallel));

        TestBehaviourTreeTask behaviourTreeTask = new TestBehaviourTreeTask();
        TestParallelTask parallelTask = new TestParallelTask(parallel, behaviourTreeTask.getContext());
        for (Statusenum childStatus : childStatuses) {
            parallelTask.getNodeTaskList().add(
                    new FixedStatusActionTask(
                            childStatus, parallelTask, behaviourTreeTask.getContext()));
            parallelTask.getBehaviourStackList().add(new BehaviourStack());
        }
        return parallelTask;
    }

    private static final class TestParallelTask extends CompParallelTask {
        private TestParallelTask(CompParallel atom, BehaviourContext context) {
            super(atom, null, context);
        }

        @Override
        public void log(Level level, String message) {
        }
    }

    private static final class InterruptTrackingActionTask extends ActionTask {
        private InterruptTrackingActionTask(
                Statusenum status,
                BehaviourTask<?> parent,
                BehaviourContext context) {
            super(new InterruptTrackingAction(status), parent, context);
        }

        private int getInterruptCount() {
            return ((InterruptTrackingAction) getAtom()).getInterruptCount();
        }

        @Override
        public void log(Level level, String message) {
        }
    }

    private static final class InterruptTrackingAction
            extends Action<config.behaviour.node.ActReturnStatus> {
        private final Statusenum status;
        private int interruptCount;

        private InterruptTrackingAction(Statusenum status) {
            super(new config.behaviour.node.ActReturnStatus(status.getId(), "测试"));
            this.status = status;
            bindExecutor(new InterruptTrackingActionExecutor(this));
        }

        private int getInterruptCount() {
            return interruptCount;
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
            return getAtom().status;
        }

        @Override
        public void onInterrupt(NodeRuntime runtime) {
            getAtom().interruptCount++;
        }

        @Override
        public void reset(NodeRuntime runtime, boolean recursive) {
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
