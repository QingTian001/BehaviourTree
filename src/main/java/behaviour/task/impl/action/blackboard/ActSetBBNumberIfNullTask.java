package behaviour.task.impl.action.blackboard;

import behaviour.node.action.blackboard.ActSetBBNumberIfNull;
import behaviour.task.ActionTask;
import behaviour.BehaviourContext;
import behaviour.task.NodeTask;

/**
 * ActSetBBNumberIfNull 的运行时任务实现
 */
public class ActSetBBNumberIfNullTask extends ActionTask {

    public ActSetBBNumberIfNullTask(ActSetBBNumberIfNull atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public ActSetBBNumberIfNull getAtom() {
        return (ActSetBBNumberIfNull) super.getAtom();
    }
}
