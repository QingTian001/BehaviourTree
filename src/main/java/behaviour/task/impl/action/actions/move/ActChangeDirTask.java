package behaviour.task.impl.action.actions.move;

import config.behaviour.Statusenum;
import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.BehaviourStack;
import behaviour.runtime.action.actions.move.ActChangeDirRuntime;
import behaviour.task.ActionTask;
import behaviour.task.NodeTask;
import behaviour.node.action.actions.move.ActChangeDir;

/**
 * ActChangeDir 的运行时任务实现
 */
public class ActChangeDirTask extends ActionTask implements ActChangeDirRuntime {

    // Runtime state fields
    private GEntity target;
    private long finishTimeMs;

    public ActChangeDirTask(ActChangeDir atom, NodeTask parent, BehaviourContext context) {
        super(atom, parent, context);
    }

    @Override
    public ActChangeDir getAtom() {
        return (ActChangeDir) super.getAtom();
    }

    // Getter and setter for runtime state
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
