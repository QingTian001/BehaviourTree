package behaviour.task.impl.action.actions.move;

import behaviour.BehaviourContext;
import behaviour.node.action.actions.move.ActWalkToPosWithDir;
import behaviour.runtime.action.actions.move.ActWalkToPosWithDirRuntime;
import behaviour.task.ActionTask;
import behaviour.task.NodeTask;
import behaviour.BehaviourOperation;

/**
 * ActWalkToPosWithDir 的运行时任务实现
 * 包含运行时状态：moveToPos Job 和 finalDir
 */
public class ActWalkToPosWithDirTask extends ActionTask implements ActWalkToPosWithDirRuntime {
    private BehaviourOperation moveToPos;
    private float finalDir;

    public ActWalkToPosWithDirTask(ActWalkToPosWithDir atom, NodeTask parent, BehaviourContext context) {
        super(atom, parent, context);
    }

    @Override
    public ActWalkToPosWithDir getAtom() {
        return (ActWalkToPosWithDir) super.getAtom();
    }

    public BehaviourOperation getMoveToPos() {
        return moveToPos;
    }

    public void setMoveToPos(BehaviourOperation moveToPos) {
        this.moveToPos = moveToPos;
    }

    public float getFinalDir() {
        return finalDir;
    }

    public void setFinalDir(float finalDir) {
        this.finalDir = finalDir;
    }
}
