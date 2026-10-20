package behaviour.executor.condition;

import behaviour.BehaviourContext;
import behaviour.condition.Condition;
import behaviour.executor.Executor;
import behaviour.runtime.ConditionRuntime;

public abstract class ConditionExecutor<T extends Condition<?>> extends Executor<T> {
    protected ConditionExecutor(T atom) {
        super(atom);
    }

    public abstract boolean calculate(BehaviourContext context);

    public abstract boolean calculateAndListenEvent(ConditionRuntime runtime);

    public void reset(ConditionRuntime runtime, boolean recursive) {
        runtime.clearDirty();
        runtime.resetListeners();
    }
}
