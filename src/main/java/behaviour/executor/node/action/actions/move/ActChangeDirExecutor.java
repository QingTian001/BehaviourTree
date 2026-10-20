package behaviour.executor.node.action.actions.move;

import config.behaviour.Statusenum;
import behaviour.GEntity;
import behaviour.BehaviourStack;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.node.action.actions.move.ActChangeDir;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.action.actions.move.ActChangeDirRuntime;

public final class ActChangeDirExecutor extends ActionExecutor<ActChangeDir> {
    public ActChangeDirExecutor(ActChangeDir atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        ActChangeDirRuntime actionRuntime = (ActChangeDirRuntime) runtime;
        GEntity target = getAtom().getTargetExpr().calculate(runtime.getContext());
        if (target == null || target.isDestroyed()) {
            return false;
        }
        actionRuntime.setTarget(target);
        float dir = getAtom().getDirExpr().calculate(runtime.getContext()).floatValue();
        runtime.getContext().getHost().changeDirection(target, dir);
        actionRuntime.setFinishTimeMs(runtime.getContext().getHost().getNowMills() + ActChangeDir.DURATION_MS);
        return true;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        ActChangeDirRuntime actionRuntime = (ActChangeDirRuntime) runtime;
        return runtime.getContext().getHost().getNowMills() >= actionRuntime.getFinishTimeMs()
                ? Statusenum.BTSUCCESS
                : Statusenum.BTRUNNING;
    }

    @Override
    public void onInterrupt(NodeRuntime runtime) {
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
        ActChangeDirRuntime actionRuntime = (ActChangeDirRuntime) runtime;
        actionRuntime.setTarget(null);
        actionRuntime.setFinishTimeMs(0);
    }
}
