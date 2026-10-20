package behaviour.decorator;

import behaviour.*;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import behaviour.executor.node.decorator.DecNotExecutor;
import behaviour.node.decorator.DecNot;
import behaviour.runtime.FixedStatusActionTask;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.impl.node.decorator.DecNotTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class DecNotRuntimeTest {
    @Test
    @DisplayName("子节点成功时 DecNot 返回失败")
    void successChildIsInvertedToFailure() {
        BehaviourStack stack = new BehaviourStack();
        TestDecNotTask decorator = createDecorator(Statusenum.BTSUCCESS, false);

        Statusenum result = decorator.update(stack);

        assertEquals(Statusenum.BTFAILURE, result);
        assertEquals(Statusenum.BTINVALID, decorator.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("子节点失败时 DecNot 返回成功")
    void failureChildIsInvertedToSuccess() {
        BehaviourStack stack = new BehaviourStack();
        TestDecNotTask decorator = createDecorator(Statusenum.BTFAILURE, false);

        Statusenum result = decorator.update(stack);

        assertEquals(Statusenum.BTSUCCESS, result);
        assertEquals(Statusenum.BTINVALID, decorator.getStatus());
        assertNull(stack.peekRunningNode());
    }

    @Test
    @DisplayName("validOnFinish 为 false 时运行中的子节点会被立即装饰")
    void runningChildIsDecoratedImmediatelyWhenValidOnFinishIsFalse() {
        BehaviourStack stack = new BehaviourStack();
        TestDecNotTask decorator = createDecorator(Statusenum.BTRUNNING, false);

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
        TestDecNotTask decorator = createDecorator(Statusenum.BTRUNNING, true);

        Statusenum result = decorator.update(stack);

        assertEquals(Statusenum.BTRUNNING, result);
        assertEquals(Statusenum.BTRUNNING, decorator.getStatus());
        assertSame(decorator, stack.peekRunningNode());
        assertSame(decorator.getDecoratedNodeRuntime(),
                decorator.getDecorateNodeStack().peekRunningNode());
    }

    private TestDecNotTask createDecorator(Statusenum childStatus, boolean validOnFinish) {
        config.behaviour.node.DecNot decoratorConfig = new config.behaviour.node.DecNot(
                validOnFinish,
                new config.behaviour.node.ActReturnStatus(childStatus.getId(), "测试"),
                "测试");
        DecNot decorator = new DecNot(decoratorConfig);
        decorator.bindExecutor(new DecNotExecutor(decorator));

        TestBehaviourTreeTask behaviourTreeTask = new TestBehaviourTreeTask();
        TestDecNotTask decoratorTask = new TestDecNotTask(decorator, behaviourTreeTask.getContext());

        decoratorTask.setDecoratedNodeRuntime(
                new FixedStatusActionTask(
                        childStatus, decoratorTask, behaviourTreeTask.getContext()));
        return decoratorTask;
    }

    private static final class TestDecNotTask extends DecNotTask {
        private TestDecNotTask(DecNot atom, BehaviourContext context) {
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
