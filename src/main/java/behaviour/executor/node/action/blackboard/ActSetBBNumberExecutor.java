package behaviour.executor.node.action.blackboard;

import config.behaviour.Statusenum;
import behaviour.BehaviourStack;
import behaviour.TypedNumber;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.node.action.blackboard.ActSetBBNumber;
import behaviour.runtime.NodeRuntime;

public final class ActSetBBNumberExecutor extends ActionExecutor<ActSetBBNumber> {
    public ActSetBBNumberExecutor(ActSetBBNumber atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        return true;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
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
