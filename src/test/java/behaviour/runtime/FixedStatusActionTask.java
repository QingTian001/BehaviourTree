package behaviour.runtime;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import behaviour.BehaviourContext;
import behaviour.BehaviourStack;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.node.action.Action;
import behaviour.task.ActionTask;
import behaviour.task.BehaviourTask;

public class FixedStatusActionTask extends ActionTask {
    public FixedStatusActionTask(
            Statusenum status,
            BehaviourTask<?> parent,
            BehaviourContext context) {
        super(new FixedStatusAction(status), parent, context);
    }

    @Override
    public void log(Level level, String message) {
    }

    public int getUpdateCount() {
        return ((FixedStatusAction) getAtom()).getUpdateCount();
    }

    public int getInterruptCount() {
        return ((FixedStatusAction) getAtom()).getInterruptCount();
    }

    private static final class FixedStatusAction extends Action<config.behaviour.node.ActReturnStatus> {
        private final Statusenum status;
        private int updateCount;
        private int interruptCount;

        private FixedStatusAction(Statusenum status) {
            super(new config.behaviour.node.ActReturnStatus(status.getId(), "测试固定状态"));
            this.status = status;
            bindExecutor(new FixedStatusActionExecutor(this));
        }

        @Override
        public void loadFromCfg() {
        }

        private int getUpdateCount() {
            return updateCount;
        }

        private int getInterruptCount() {
            return interruptCount;
        }
    }

    private static final class FixedStatusActionExecutor extends ActionExecutor<FixedStatusAction> {
        private FixedStatusActionExecutor(FixedStatusAction atom) {
            super(atom);
        }

        @Override
        public boolean onEnter(NodeRuntime runtime) {
            return true;
        }

        @Override
        public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
            getAtom().updateCount++;
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
}
