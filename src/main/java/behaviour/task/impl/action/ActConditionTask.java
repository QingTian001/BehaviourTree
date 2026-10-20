package behaviour.task.impl.action;

import behaviour.BehaviourContext;
import behaviour.node.action.ActCondition;
import behaviour.task.NodeTask;

/**
 * ActCondition 的运行时任务实现
 */
public class ActConditionTask extends NodeTask {
    public ActConditionTask(ActCondition atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public ActCondition getAtom() {
        return (ActCondition) super.getAtom();
    }
}
