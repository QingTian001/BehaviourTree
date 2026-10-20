package behaviour.executor.node.decorator;

import config.behaviour.Statusenum;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.decorator.DecTimedRuntime;
import behaviour.node.decorator.DecTimed;
import behaviour.runtime.node.decorator.StatusDecoratorRuntime;

public final class DecTimedExecutor extends StatusDecoratorExecutor<DecTimed> {
    public DecTimedExecutor(DecTimed atom) {
        super(atom);
    }

    @Override
    protected Statusenum applyDecoration(StatusDecoratorRuntime runtime, Statusenum childStatus) {
        DecTimedRuntime timedRuntime = (DecTimedRuntime) runtime;
        long nowMs = runtime.getContext().getHost().getNowMills();
        if (nowMs - timedRuntime.getStartTimeMs() >= timedRuntime.getTimeMs()) {
            return Statusenum.BTSUCCESS;
        }
        return Statusenum.BTRUNNING;
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        if (!super.onEnter(runtime)) {
            return false;
        }
        DecTimedRuntime decTimedRuntime = (DecTimedRuntime) runtime;
        decTimedRuntime.setStartTimeMs(runtime.getContext().getHost().getNowMills());
        decTimedRuntime.setTimeMs(getAtom().getTimeMsExpr().calculate(runtime.getContext()).doubleValue());
        return true;
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
        super.reset(runtime, recursive);
        DecTimedRuntime decTimedRuntime = (DecTimedRuntime) runtime;
        decTimedRuntime.setStartTimeMs(0);
        decTimedRuntime.setTimeMs(0);
    }
}
