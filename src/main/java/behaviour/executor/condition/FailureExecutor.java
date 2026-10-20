package behaviour.executor.condition;

import behaviour.BehaviourContext;
import behaviour.condition.Failure;
import behaviour.runtime.ConditionRuntime;

public final class FailureExecutor extends ConditionExecutor<Failure> {
    public FailureExecutor(Failure atom) {
        super(atom);
    }

    @Override
    public boolean calculate(BehaviourContext context) {
        return false;
    }

    @Override
    public boolean calculateAndListenEvent(ConditionRuntime runtime) {
        return false;
    }
}
