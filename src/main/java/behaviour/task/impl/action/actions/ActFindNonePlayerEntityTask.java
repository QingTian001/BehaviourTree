package behaviour.task.impl.action.actions;

import behaviour.BehaviourContext;
import behaviour.node.action.actions.ActFindNonePlayerEntity;
import behaviour.runtime.action.actions.ActFindNonePlayerEntityRuntime;
import behaviour.task.ActionTask;
import behaviour.task.NodeTask;

/**
 * ActFindNonePlayerEntity 的运行时任务实现
 */
public class ActFindNonePlayerEntityTask extends ActionTask implements ActFindNonePlayerEntityRuntime {

    // 运行时状态
    private long lastEnterTimestamp = Long.MIN_VALUE;

    public ActFindNonePlayerEntityTask(ActFindNonePlayerEntity atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public ActFindNonePlayerEntity getAtom() {
        return (ActFindNonePlayerEntity) super.getAtom();
    }

    public long getLastEnterTimestamp() {
        return lastEnterTimestamp;
    }

    public void setLastEnterTimestamp(long lastEnterTimestamp) {
        this.lastEnterTimestamp = lastEnterTimestamp;
    }
}