package behaviour.executor.node.action.blackboard;

import config.behaviour.Statusenum;
import behaviour.BehaviourStack;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.node.action.blackboard.ActSetBBObj;
import behaviour.runtime.NodeRuntime;

public final class ActSetBBObjExecutor extends ActionExecutor<ActSetBBObj> {
    public ActSetBBObjExecutor(ActSetBBObj atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        return true;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        Object value = getAtom().getExpressionObject().calculate(runtime.getContext());
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
