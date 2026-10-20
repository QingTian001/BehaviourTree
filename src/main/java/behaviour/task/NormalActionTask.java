package behaviour.task;

import behaviour.BehaviourContext;
import behaviour.node.action.Action;

/**
 * NormalActionTask for stateless Actions.
 * Used as a generic Task for Actions that don't need specific runtime state.
 */
public class NormalActionTask extends ActionTask {

    public NormalActionTask(Action<?> atom, BehaviourTask<?> parent, BehaviourContext context) {
        super(atom, parent, context);
    }
}
