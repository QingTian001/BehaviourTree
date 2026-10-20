package behaviour.executor.condition;

import behaviour.BehaviourContext;
import behaviour.condition.Success;
import behaviour.runtime.ConditionRuntime;

public final class SuccessExecutor extends ConditionExecutor<Success> {
    public SuccessExecutor(Success atom) {
        super(atom);
    }

    @Override
    public boolean calculate(BehaviourContext context) {
        return true;
    }

    @Override
    public boolean calculateAndListenEvent(ConditionRuntime runtime) {
        return true;
    }
}
