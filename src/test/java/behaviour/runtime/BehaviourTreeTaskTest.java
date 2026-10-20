package behaviour.runtime;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import behaviour.task.BehaviourTreeTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class BehaviourTreeTaskTest {
    @Test
    @DisplayName("顶层行为树中断运行中的根节点并清理状态")
    void interruptRunningRootTaskCleansTreeState() {
        TestBehaviourTreeTask treeTask = new TestBehaviourTreeTask();
        FixedStatusActionTask rootTask = new FixedStatusActionTask(
                Statusenum.BTRUNNING, null, treeTask.getContext());
        treeTask.initRootNodeTask(rootTask);

        Statusenum result = treeTask.update();

        assertEquals(Statusenum.BTRUNNING, result);
        assertEquals(Statusenum.BTRUNNING, treeTask.getStatus());
        assertSame(rootTask, treeTask.getBehaviourStack().peekRunningNode());

        treeTask.interrupt();

        assertEquals(Statusenum.BTINVALID, treeTask.getStatus());
        assertEquals(Statusenum.BTINVALID, rootTask.getStatus());
        assertEquals(1, rootTask.getInterruptCount());
        assertNull(treeTask.getBehaviourStack().peekRunningNode());
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
