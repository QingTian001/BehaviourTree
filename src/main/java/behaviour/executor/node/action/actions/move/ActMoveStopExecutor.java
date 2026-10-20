package behaviour.executor.node.action.actions.move;

import config.behaviour.Statusenum;
import behaviour.GEntity;
import behaviour.BehaviourStack;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.node.action.actions.move.ActMoveStop;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.action.actions.move.ActMoveStopRuntime;

public final class ActMoveStopExecutor extends ActionExecutor<ActMoveStop> {
    public ActMoveStopExecutor(ActMoveStop atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        ActMoveStopRuntime actionRuntime = (ActMoveStopRuntime) runtime;
        GEntity target = getAtom().getTargetExpr().calculate(runtime.getContext());
        if (target == null || target.isDestroyed()) {
            return false;
        }
        actionRuntime.setTarget(target);
        runtime.getContext().getHost().stopMove(target);
        return true;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        return Statusenum.BTSUCCESS;
    }

    @Override
    public void onInterrupt(NodeRuntime runtime) {
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
        ActMoveStopRuntime actionRuntime = (ActMoveStopRuntime) runtime;
        actionRuntime.setTarget(null);
    }
}
