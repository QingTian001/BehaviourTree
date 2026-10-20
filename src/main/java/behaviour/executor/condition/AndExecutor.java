package behaviour.executor.condition;

import behaviour.BehaviourContext;
import behaviour.condition.And;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.condition.AndRuntime;

public final class AndExecutor extends ConditionExecutor<And> {
    public AndExecutor(And atom) {
        super(atom);
    }

    @Override
    public boolean calculate(BehaviourContext context) {
        return getAtom().getLeftCond().calculate(context) && getAtom().getRightCond().calculate(context);
    }

    @Override
    public boolean calculateAndListenEvent(ConditionRuntime runtime) {
        AndRuntime andRuntime = (AndRuntime) runtime;
        return andRuntime.getLeftCondRuntime().calculateAndListenEvent()
                && andRuntime.getRightCondRuntime().calculateAndListenEvent();
    }

    @Override
    public void reset(ConditionRuntime runtime, boolean recursive) {
        super.reset(runtime, recursive);
        AndRuntime andRuntime = (AndRuntime) runtime;
        if (andRuntime.getLeftCondRuntime() != null) {
            andRuntime.getLeftCondRuntime().reset(recursive);
        }
        if (andRuntime.getRightCondRuntime() != null) {
            andRuntime.getRightCondRuntime().reset(recursive);
        }
    }
}
