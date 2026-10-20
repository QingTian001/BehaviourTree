package behaviour.task.impl.action.blackboard;

import behaviour.node.action.blackboard.ActSetBBString;
import behaviour.task.ActionTask;
import behaviour.BehaviourContext;
import behaviour.task.NodeTask;

/**
 * ActSetBBString 的运行时任务实现
 */
public class ActSetBBStringTask extends ActionTask {

    public ActSetBBStringTask(ActSetBBString atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public ActSetBBString getAtom() {
        return (ActSetBBString) super.getAtom();
    }
}
