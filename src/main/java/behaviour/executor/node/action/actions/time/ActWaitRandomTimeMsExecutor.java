package behaviour.executor.node.action.actions.time;

import config.behaviour.Statusenum;
import behaviour.BehaviourStack;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.node.action.actions.time.ActWaitRandomTimeMs;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.action.actions.time.ActWaitRandomTimeMsRuntime;
import util.MathUtil;

public final class ActWaitRandomTimeMsExecutor extends ActionExecutor<ActWaitRandomTimeMs> {
    public ActWaitRandomTimeMsExecutor(ActWaitRandomTimeMs atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        ActWaitRandomTimeMsRuntime actionRuntime = (ActWaitRandomTimeMsRuntime) runtime;
        long startTimeMs = runtime.getContext().getHost().getNowMills();
        double timeMs = MathUtil.randBetween(getAtom().getAtomCfg().getMinTimeMs(), getAtom().getAtomCfg().getMaxTimeMs());
        actionRuntime.setStartTimeMs(startTimeMs);
        actionRuntime.setTimeMs(timeMs);
        return true;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        ActWaitRandomTimeMsRuntime actionRuntime = (ActWaitRandomTimeMsRuntime) runtime;
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
        ActWaitRandomTimeMsRuntime actionRuntime = (ActWaitRandomTimeMsRuntime) runtime;
        actionRuntime.setStartTimeMs(0);
        actionRuntime.setTimeMs(0);
    }
}
