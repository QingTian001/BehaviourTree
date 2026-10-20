package behaviour.task.impl.action;

import behaviour.BehaviourContext;
import behaviour.node.action.ActReturnStatus;
import behaviour.task.ActionTask;
import behaviour.task.NodeTask;

/**
 * ActReturnStatus 的运行时任务实现
 */
public class ActReturnStatusTask extends ActionTask {

    public ActReturnStatusTask(ActReturnStatus atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public ActReturnStatus getAtom() {
        return (ActReturnStatus) super.getAtom();
    }
}
