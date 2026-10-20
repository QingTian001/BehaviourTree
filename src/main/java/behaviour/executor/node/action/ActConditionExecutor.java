package behaviour.executor.node.action;

import config.behaviour.Statusenum;
import behaviour.BehaviourStack;
import behaviour.executor.node.NodeExecutor;
import behaviour.node.action.ActCondition;
import behaviour.runtime.NodeRuntime;

public class ActConditionExecutor extends NodeExecutor<ActCondition> {
    public ActConditionExecutor(ActCondition atom) {
        super(atom);
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        boolean result = getAtom().getCond().calculate(runtime.getContext());
        return result ? Statusenum.BTSUCCESS : Statusenum.BTFAILURE;
    }
}
