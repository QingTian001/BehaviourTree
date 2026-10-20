package behaviour.executor.condition;

import behaviour.BehaviourContext;
import behaviour.condition.Or;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.condition.OrRuntime;

public final class OrExecutor extends ConditionExecutor<Or> {
    public OrExecutor(Or atom) {
        super(atom);
    }

    @Override
    public boolean calculate(BehaviourContext context) {
        return getAtom().getLeftCond().calculate(context) || getAtom().getRightCond().calculate(context);
    }

    @Override
    public boolean calculateAndListenEvent(ConditionRuntime runtime) {
        OrRuntime orRuntime = (OrRuntime) runtime;
        return orRuntime.getLeftCondRuntime().calculateAndListenEvent()
                || orRuntime.getRightCondRuntime().calculateAndListenEvent();
    }

    @Override
    public void reset(ConditionRuntime runtime, boolean recursive) {
        super.reset(runtime, recursive);
        OrRuntime orRuntime = (OrRuntime) runtime;
        if (orRuntime.getLeftCondRuntime() != null) {
            orRuntime.getLeftCondRuntime().reset(recursive);
        }
        if (orRuntime.getRightCondRuntime() != null) {
            orRuntime.getRightCondRuntime().reset(recursive);
        }
    }
}
