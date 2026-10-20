package behaviour.decorator;

import behaviour.*;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import behaviour.executor.node.decorator.DecLoopExecutor;
import behaviour.node.decorator.DecLoop;
import behaviour.runtime.FixedStatusActionTask;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.impl.node.decorator.DecLoopTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class DecLoopRuntimeTest {
    @Test
    @DisplayName("循环次数为 0 时子节点完成后立即成功")
    void zeroLoopCountFinishesAfterChildCompletes() {
        BehaviourStack stack = new BehaviourStack();
        TestDecLoopTask loop = createLoop(0, Statusenum.BTSUCCESS);

        Statusenum result = loop.update(stack);

        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(0, loop.getLoopNum());
        assertEquals(Statusenum.BTINVALID, loop.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("循环次数为 0 时会中断仍在运行的子节点")
    void zeroLoopCountInterruptsRunningChild() {
        BehaviourStack stack = new BehaviourStack();
        TestDecLoopTask loop = createLoop(0, Statusenum.BTRUNNING);
        FixedStatusActionTask child = (FixedStatusActionTask) loop.getDecoratedNodeRuntime();

        Statusenum result = loop.update(stack);

        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(1, child.getInterruptCount());
        assertEquals(Statusenum.BTINVALID, loop.getStatus());
        assertNull(stack.peekRunningNode());
        assertNull(loop.getDecorateNodeStack().peekRunningNode());
    }

    @Test
    @DisplayName("有限循环会在达到次数后成功")
    void finiteLoopFinishesAfterConfiguredNumberOfRepeats() {
        BehaviourStack stack = new BehaviourStack();
        TestDecLoopTask loop = createLoop(1, Statusenum.BTSUCCESS);

        assertEquals(Statusenum.BTRUNNING, loop.update(stack));
        assertEquals(1, loop.getLoopNum());
        assertSame(loop, stack.peekRunningNode());

        Statusenum result = loop.update(stack);

        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(0, loop.getLoopNum());
        assertEquals(Statusenum.BTINVALID, loop.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("循环次数为 2 时会经历两个运行周期后成功")
    void loopCountTwoKeepsRunningForTwoCycles() {
        BehaviourStack stack = new BehaviourStack();
        TestDecLoopTask loop = createLoop(2, Statusenum.BTSUCCESS);

        assertEquals(Statusenum.BTRUNNING, loop.update(stack));
        assertEquals(1, loop.getLoopNum());
        assertEquals(Statusenum.BTRUNNING, loop.update(stack));
        assertEquals(2, loop.getLoopNum());

        Statusenum result = loop.update(stack);

        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(0, loop.getLoopNum());
        assertEquals(Statusenum.BTINVALID, loop.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("有限循环会按配置次数重复执行子节点")
    void finiteLoopExecutesChildConfiguredNumberOfTimes() {
        DecLoop loop = new DecLoop(new config.behaviour.node.DecLoop(
                false,
                new config.behaviour.node.ActReturnStatus(Statusenum.BTSUCCESS.getId(), "测试"),
                2,
                "测试"));
        loop.bindExecutor(new DecLoopExecutor(loop));

        TestBehaviourTreeTask behaviourTreeTask = new TestBehaviourTreeTask();
        TestDecLoopTask loopTask = new TestDecLoopTask(loop, behaviourTreeTask.getContext());
        FixedStatusActionTask childTask = new FixedStatusActionTask(
                Statusenum.BTSUCCESS, loopTask, behaviourTreeTask.getContext());
        loopTask.setDecoratedNodeRuntime(childTask);

        BehaviourStack stack = new BehaviourStack();
        loopTask.update(stack);
        loopTask.update(stack);
        loopTask.update(stack);

        assertEquals(3, childTask.getUpdateCount());
    }

    @Test
    @DisplayName("负数循环次数表示持续运行")
    void negativeLoopCountKeepsRunning() {
        BehaviourStack stack = new BehaviourStack();
        TestDecLoopTask loop = createLoop(-1, Statusenum.BTSUCCESS);

        Statusenum result = loop.update(stack);

        assertEquals(Statusenum.BTRUNNING, result);
        assertEquals(0, loop.getLoopNum());
        assertSame(loop, stack.peekRunningNode());
    }

    @Test
    @DisplayName("负数循环会持续执行子节点且不会完成")
    void negativeLoopCountContinuesExecutingChild() {
        BehaviourTreeTask behaviourTreeTask = new TestBehaviourTreeTask();
        DecLoop loop = new DecLoop(new config.behaviour.node.DecLoop(
                false,
                new config.behaviour.node.ActReturnStatus(Statusenum.BTSUCCESS.getId(), "测试"),
                -1,
                "测试"));
        loop.bindExecutor(new DecLoopExecutor(loop));
        TestDecLoopTask loopTask = new TestDecLoopTask(loop, behaviourTreeTask.getContext());
        FixedStatusActionTask childTask = new FixedStatusActionTask(
                Statusenum.BTSUCCESS, loopTask, behaviourTreeTask.getContext());
        loopTask.setDecoratedNodeRuntime(childTask);
        BehaviourStack stack = new BehaviourStack();

        assertEquals(Statusenum.BTRUNNING, loopTask.update(stack));
        assertEquals(Statusenum.BTRUNNING, loopTask.update(stack));
        assertEquals(Statusenum.BTRUNNING, loopTask.update(stack));

        assertEquals(0, loopTask.getLoopNum());
        assertEquals(3, childTask.getUpdateCount());
        assertSame(loopTask, stack.peekRunningNode());
    }

    @Test
    @DisplayName("失败子节点也会被 DecLoop 继续执行")
    void failedChildIsRetriedByFiniteLoop() {
        BehaviourTreeTask behaviourTreeTask = new TestBehaviourTreeTask();
        DecLoop loop = new DecLoop(new config.behaviour.node.DecLoop(
                false,
                new config.behaviour.node.ActReturnStatus(Statusenum.BTFAILURE.getId(), "测试"),
                1,
                "测试"));
        loop.bindExecutor(new DecLoopExecutor(loop));
        TestDecLoopTask loopTask = new TestDecLoopTask(loop, behaviourTreeTask.getContext());
        FixedStatusActionTask childTask = new FixedStatusActionTask(
                Statusenum.BTFAILURE, loopTask, behaviourTreeTask.getContext());
        loopTask.setDecoratedNodeRuntime(childTask);
        BehaviourStack stack = new BehaviourStack();

        assertEquals(Statusenum.BTRUNNING, loopTask.update(stack));
        assertEquals(1, childTask.getUpdateCount());
        assertEquals(Statusenum.BTSUCCESS, loopTask.update(stack));
        assertEquals(2, childTask.getUpdateCount());
        assertNull(stack.peekRunningNode());
    }

    private TestDecLoopTask createLoop(int loopCount, Statusenum childStatus) {
        DecLoop loop = new DecLoop(new config.behaviour.node.DecLoop(
                false,
                new config.behaviour.node.ActReturnStatus(childStatus.getId(), "测试"),
                loopCount,
                "测试"));
        loop.bindExecutor(new DecLoopExecutor(loop));

        TestBehaviourTreeTask behaviourTreeTask = new TestBehaviourTreeTask();
        TestDecLoopTask loopTask = new TestDecLoopTask(loop, behaviourTreeTask.getContext());
        loopTask.setDecoratedNodeRuntime(
                new FixedStatusActionTask(childStatus, loopTask, behaviourTreeTask.getContext()));
        return loopTask;
    }

    private static final class TestDecLoopTask extends DecLoopTask {
        private TestDecLoopTask(DecLoop atom, BehaviourContext context) {
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
