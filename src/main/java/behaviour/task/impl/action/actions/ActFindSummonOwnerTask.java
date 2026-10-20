package behaviour.task.impl.action.actions;

import behaviour.BehaviourContext;
import behaviour.node.action.actions.ActFindSummonOwner;
import behaviour.runtime.action.actions.ActFindSummonOwnerRuntime;
import behaviour.task.ActionTask;
import behaviour.task.NodeTask;

/**
 * ActFindSummonOwner 的运行时任务实现
 */
public class ActFindSummonOwnerTask extends ActionTask implements ActFindSummonOwnerRuntime {

    // 运行时状态
    private long ownerInstId;

    public ActFindSummonOwnerTask(ActFindSummonOwner atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public ActFindSummonOwner getAtom() {
        return (ActFindSummonOwner) super.getAtom();
    }

    public long getOwnerInstId() {
        return ownerInstId;
    }

    public void setOwnerInstId(long ownerInstId) {
        this.ownerInstId = ownerInstId;
    }
}