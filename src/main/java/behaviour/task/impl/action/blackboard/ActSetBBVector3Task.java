package behaviour.task.impl.action.blackboard;

import behaviour.node.action.blackboard.ActSetBBVector3;
import behaviour.task.ActionTask;
import behaviour.BehaviourContext;
import behaviour.task.NodeTask;

/**
 * ActSetBBVector3 的运行时任务实现
 */
public class ActSetBBVector3Task extends ActionTask {

    public ActSetBBVector3Task(ActSetBBVector3 atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public ActSetBBVector3 getAtom() {
        return (ActSetBBVector3) super.getAtom();
    }
}
