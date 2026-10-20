package behaviour.task.impl.action.actions.move;

import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.node.action.actions.move.ActMoveStop;
import behaviour.runtime.action.actions.move.ActMoveStopRuntime;
import behaviour.task.ActionTask;
import behaviour.task.NodeTask;

/**
 * ActMoveStop 的运行时任务实现
 */
public class ActMoveStopTask extends ActionTask implements ActMoveStopRuntime {
    private GEntity target;

    public ActMoveStopTask(ActMoveStop atom, NodeTask parent, BehaviourContext context) {
        super(atom, parent, context);
    }

    @Override
    public ActMoveStop getAtom() {
        return (ActMoveStop) super.getAtom();
    }

    public GEntity getTarget() {
        return target;
    }

    public void setTarget(GEntity target) {
        this.target = target;
    }
}
