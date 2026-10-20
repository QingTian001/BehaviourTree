package behaviour.task.impl.action;

import behaviour.BehaviourContext;
import behaviour.node.action.ActDebugMsg;
import behaviour.runtime.action.ActDebugMsgRuntime;
import behaviour.task.ActionTask;
import behaviour.task.NodeTask;

/**
 * ActDebugMsg 的运行时任务实现
 * 由 migrate_actions.py 自动生成
 */
public class ActDebugMsgTask extends ActionTask implements ActDebugMsgRuntime {
    public ActDebugMsgTask(ActDebugMsg atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public ActDebugMsg getAtom() {
        return (ActDebugMsg) super.getAtom();
    }
}
