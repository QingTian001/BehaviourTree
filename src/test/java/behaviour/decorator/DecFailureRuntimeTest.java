package behaviour.decorator;

import behaviour.*;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import behaviour.executor.node.decorator.DecFailureExecutor;
import behaviour.node.decorator.DecFailure;
import behaviour.runtime.FixedStatusActionTask;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.impl.node.decorator.DecFailureTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class DecFailureRuntimeTest {
    @Test
    @DisplayName("子节点成功时 DecFailure 仍返回失败")
    void successChildIsForcedToFailure() {
        BehaviourStack stack = new BehaviourStack();
        TestDecFailureTask decorator = createDecorator(Statusenum.BTSUCCESS, false);

        Statusenum result = decorator.update(stack);

        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(Statusenum.BTINVALID, decorator.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("子节点失败时 DecFailure 返回失败")
    void failureChildReturnsFailure() {
        BehaviourStack stack = new BehaviourStack();
        TestDecFailureTask decorator = createDecorator(Statusenum.BTFAILURE, false);

        Statusenum result = decorator.update(stack);

        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(Statusenum.BTINVALID, decorator.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("validOnFinish 为 false 时运行中的子节点也立即返回失败")
    void runningChildIsForcedToFailureWhenValidOnFinishIsFalse() {
        BehaviourStack stack = new BehaviourStack();
        TestDecFailureTask decorator = createDecorator(Statusenum.BTRUNNING, false);

        Statusenum result = decorator.update(stack);

        FixedStatusActionTask child = (FixedStatusActionTask) decorator.getDecoratedNodeRuntime();
        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(1, child.getInterruptCount());
        assertEquals(Statusenum.BTINVALID, decorator.getStatus());
        assertNull(stack.peekRunningNode());
        assertNull(decorator.getDecorateNodeStack().peekRunningNode());
    }

    @Test
    @DisplayName("validOnFinish 为 true 时运行中的子节点继续运行")
    void runningChildContinuesWhenValidOnFinishIsTrue() {
        BehaviourStack stack = new BehaviourStack();
        TestDecFailureTask decorator = createDecorator(Statusenum.BTRUNNING, true);

        Statusenum result = decorator.update(stack);

        assertEquals(Statusenum.BTRUNNING, result);
        assertEquals(Statusenum.BTRUNNING, decorator.getStatus());
        assertSame(decorator, stack.peekRunningNode());
        assertSame(decorator.getDecoratedNodeRuntime(),
                decorator.getDecorateNodeStack().peekRunningNode());
    }

    private TestDecFailureTask createDecorator(Statusenum childStatus, boolean validOnFinish) {
        config.behaviour.node.DecFailure decoratorConfig = new config.behaviour.node.DecFailure(
                validOnFinish,
                new config.behaviour.node.ActReturnStatus(childStatus.getId(), "测试"),
                "测试");
        DecFailure decorator = new DecFailure(decoratorConfig);
        decorator.bindExecutor(new DecFailureExecutor(decorator));

        TestBehaviourTreeTask behaviourTreeTask = new TestBehaviourTreeTask();
        TestDecFailureTask decoratorTask = new TestDecFailureTask(
                decorator, behaviourTreeTask.getContext());

        decoratorTask.setDecoratedNodeRuntime(
                new FixedStatusActionTask(
                        childStatus, decoratorTask, behaviourTreeTask.getContext()));
        return decoratorTask;
    }

    private static final class TestDecFailureTask extends DecFailureTask {
        private TestDecFailureTask(DecFailure atom, BehaviourContext context) {
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
