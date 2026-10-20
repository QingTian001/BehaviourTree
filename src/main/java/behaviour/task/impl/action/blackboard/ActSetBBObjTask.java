package behaviour.task.impl.action.blackboard;

import behaviour.node.action.blackboard.ActSetBBObj;
import behaviour.task.ActionTask;
import behaviour.BehaviourContext;
import behaviour.task.NodeTask;

/**
 * ActSetBBObj 的运行时任务实现
 */
public class ActSetBBObjTask extends ActionTask {

    public ActSetBBObjTask(ActSetBBObj atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public ActSetBBObj getAtom() {
        return (ActSetBBObj) super.getAtom();
    }
}
