package behaviour.task.impl.action.actions.move;

import config.behaviour.Statusenum;
import behaviour.BehaviourContext;
import behaviour.BehaviourStack;
import behaviour.node.action.actions.move.ActWalkToPos;
import behaviour.runtime.action.actions.move.ActWalkToPosRuntime;
import behaviour.task.ActionTask;
import behaviour.task.NodeTask;
import behaviour.BehaviourOperation;

/**
 * ActWalkToPos 的运行时任务实现
 * 包含运行时状态：moveToPos Job
 */
public class ActWalkToPosTask extends ActionTask implements ActWalkToPosRuntime {
    private BehaviourOperation moveToPos;

    public ActWalkToPosTask(ActWalkToPos atom, NodeTask parent, BehaviourContext context) {
        super(atom, parent, context);
    }

    @Override
    public ActWalkToPos getAtom() {
        return (ActWalkToPos) super.getAtom();
    }

    public BehaviourOperation getMoveToPos() {
        return moveToPos;
    }

    public void setMoveToPos(BehaviourOperation moveToPos) {
        this.moveToPos = moveToPos;
    }
}
