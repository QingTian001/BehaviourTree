package behaviour.executor.condition;

import behaviour.BehaviourContext;
import behaviour.condition.Not;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.condition.NotRuntime;

public final class NotExecutor extends ConditionExecutor<Not> {
    public NotExecutor(Not atom) {
        super(atom);
    }

    @Override
    public boolean calculate(BehaviourContext context) {
        return !getAtom().getCond().calculate(context);
    }

    @Override
    public boolean calculateAndListenEvent(ConditionRuntime runtime) {
        NotRuntime notRuntime = (NotRuntime) runtime;
        return !notRuntime.getCondRuntime().calculateAndListenEvent();
    }

    @Override
    public void reset(ConditionRuntime runtime, boolean recursive) {
        super.reset(runtime, recursive);
        NotRuntime notRuntime = (NotRuntime) runtime;
        if (notRuntime.getCondRuntime() != null) {
            notRuntime.getCondRuntime().reset(recursive);
        }
    }
}
