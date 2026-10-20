package behaviour.task.impl.action.actions.move;

import config.behaviour.Statusenum;
import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.BehaviourStack;
import behaviour.runtime.action.actions.move.ActFaceToEntityRuntime;
import behaviour.task.ActionTask;
import behaviour.task.NodeTask;
import behaviour.node.action.actions.move.ActFaceToEntity;

/**
 * ActFaceToEntity 的运行时任务实现
 */
public class ActFaceToEntityTask extends ActionTask implements ActFaceToEntityRuntime {
    private GEntity target;
    private long finishTimeMs;

    public ActFaceToEntityTask(ActFaceToEntity atom, NodeTask parent, BehaviourContext context) {
        super(atom, parent, context);
    }

    @Override
    public ActFaceToEntity getAtom() {
        return (ActFaceToEntity) super.getAtom();
    }

    public GEntity getTarget() {
        return target;
    }

    public void setTarget(GEntity target) {
        this.target = target;
    }

    public long getFinishTimeMs() {
        return finishTimeMs;
    }

    public void setFinishTimeMs(long finishTimeMs) {
        this.finishTimeMs = finishTimeMs;
    }
}
