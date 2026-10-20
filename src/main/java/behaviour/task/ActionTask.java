package behaviour.task;

import behaviour.BehaviourContext;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.node.action.Action;
import behaviour.runtime.ActionRuntime;

/**
 * Action node runtime task base class.
 * Subclasses can add specific runtime state (e.g., move progress).
 */
public class ActionTask extends NodeTask implements ActionRuntime {
    public ActionTask(Action<?> atom, BehaviourTask<?> parent, BehaviourContext context) {
        super(atom, parent, context);
    }

    @Override
    public Action<?> getAtom() {
        return (Action<?>) super.getAtom();
    }

    @Override
    public ActionExecutor<?> getExecutor() {
        return super.getExecutor(ActionExecutor.class);
    }
}
