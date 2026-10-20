package behaviour.executor.node.action.actions.time;

import config.behaviour.Statusenum;
import behaviour.BehaviourStack;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.node.action.actions.time.ActWaitTimeMs;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.action.actions.time.ActWaitTimeMsRuntime;

public final class ActWaitTimeMsExecutor extends ActionExecutor<ActWaitTimeMs> {
    public ActWaitTimeMsExecutor(ActWaitTimeMs atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        ActWaitTimeMsRuntime actionRuntime = (ActWaitTimeMsRuntime) runtime;
        long startTimeMs = runtime.getContext().getHost().getNowMills();
        double timeMs = getAtom().getTimeMsExpr().calculate(runtime.getContext()).doubleValue();
        actionRuntime.setStartTimeMs(startTimeMs);
        actionRuntime.setTimeMs(timeMs);
        return true;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        ActWaitTimeMsRuntime actionRuntime = (ActWaitTimeMsRuntime) runtime;
        long nowMs = runtime.getContext().getHost().getNowMills();
        return nowMs - actionRuntime.getStartTimeMs() >= actionRuntime.getTimeMs()
                ? Statusenum.BTSUCCESS
                : Statusenum.BTRUNNING;
    }

    @Override
    public void onInterrupt(NodeRuntime runtime) {
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
        ActWaitTimeMsRuntime actionRuntime = (ActWaitTimeMsRuntime) runtime;
        actionRuntime.setStartTimeMs(0);
        actionRuntime.setTimeMs(0);
    }
}
