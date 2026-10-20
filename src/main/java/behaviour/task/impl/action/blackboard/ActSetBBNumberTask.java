package behaviour.task.impl.action.blackboard;

import behaviour.node.action.blackboard.ActSetBBNumber;
import behaviour.task.ActionTask;
import behaviour.BehaviourContext;
import behaviour.task.NodeTask;

/**
 * ActSetBBNumber 的运行时任务实现
 */
public class ActSetBBNumberTask extends ActionTask {

    public ActSetBBNumberTask(ActSetBBNumber atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public ActSetBBNumber getAtom() {
        return (ActSetBBNumber) super.getAtom();
    }
}
