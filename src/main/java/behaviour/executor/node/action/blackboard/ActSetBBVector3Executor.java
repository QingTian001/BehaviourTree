package behaviour.executor.node.action.blackboard;

import config.behaviour.Statusenum;
import behaviour.BehaviourStack;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.node.action.blackboard.ActSetBBVector3;
import behaviour.runtime.NodeRuntime;
import math.Vector3;

public final class ActSetBBVector3Executor extends ActionExecutor<ActSetBBVector3> {
    public ActSetBBVector3Executor(ActSetBBVector3 atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        return true;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        Vector3 value = getAtom().getExpressionVector3().calculate(runtime.getContext());
        runtime.getContext().getBlackBoard().putValue(getAtom().getAtomCfg().getBbKey(), value);
        return Statusenum.BTSUCCESS;
    }

    @Override
    public void onInterrupt(NodeRuntime runtime) {
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
    }
}
