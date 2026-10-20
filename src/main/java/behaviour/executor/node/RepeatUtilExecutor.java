package behaviour.executor.node;

import config.behaviour.Statusenum;
import behaviour.BehaviourStack;
import behaviour.node.RepeatUtil;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.RepeatUtilRuntime;

public class RepeatUtilExecutor extends NodeExecutor<RepeatUtil> {
    public RepeatUtilExecutor(RepeatUtil atom) {
        super(atom);
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        RepeatUtilRuntime repeatUtilRuntime = (RepeatUtilRuntime) runtime;
        while (true) {
            boolean ret = getAtom().getCondition().calculate(runtime.getContext());
            if (ret) {
                return Statusenum.BTSUCCESS;
            }
            Statusenum status = repeatUtilRuntime.getDecoratedNodeTask().update(stack);
            if (status == Statusenum.BTRUNNING) {
                throw new RuntimeException("RepeatUtil node should not return Running");
            }
        }
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
        RepeatUtilRuntime repeatUtilRuntime = (RepeatUtilRuntime) runtime;
        if (repeatUtilRuntime.getDecoratedNodeTask() != null) {
            repeatUtilRuntime.getDecoratedNodeTask().reset(recursive);
        }
    }
}
