package behaviour.executor.node.action;

import config.behaviour.Statusenum;
import behaviour.BehaviourStack;
import behaviour.node.action.ActReturnStatus;
import behaviour.runtime.NodeRuntime;

public final class ActReturnStatusExecutor extends ActionExecutor<ActReturnStatus> {
    public ActReturnStatusExecutor(ActReturnStatus atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        return true;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        return Statusenum.get(getAtom().getAtomCfg().getStatus());
    }

    @Override
    public void onInterrupt(NodeRuntime runtime) {
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
    }
}
