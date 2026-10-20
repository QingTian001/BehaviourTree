package behaviour.executor.node.action.blackboard;

import config.behaviour.Statusenum;
import behaviour.BehaviourStack;
import behaviour.TypedNumber;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.node.action.blackboard.ActSetBBNumberIfNull;
import behaviour.runtime.NodeRuntime;

public final class ActSetBBNumberIfNullExecutor extends ActionExecutor<ActSetBBNumberIfNull> {
    public ActSetBBNumberIfNullExecutor(ActSetBBNumberIfNull atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        return true;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        Object o = runtime.getContext().getBlackBoard().getValue(getAtom().getAtomCfg().getBbKey());
        if (o != null) {
            return Statusenum.BTSUCCESS;
        }
        TypedNumber value = getAtom().getExpressionNumber().calculate(runtime.getContext());
        runtime.getContext().getBlackBoard().putValue(getAtom().getAtomCfg().getBbKey(), value.origin());
        return Statusenum.BTSUCCESS;
    }

    @Override
    public void onInterrupt(NodeRuntime runtime) {
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
    }
}
