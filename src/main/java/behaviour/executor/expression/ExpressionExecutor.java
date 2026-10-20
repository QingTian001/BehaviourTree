package behaviour.executor.expression;

import behaviour.BehaviourContext;
import behaviour.executor.Executor;
import behaviour.expression.Expression;
import behaviour.runtime.ExpressionRuntime;

public abstract class ExpressionExecutor<T extends Expression<?, V>, V> extends Executor<T> {
    protected ExpressionExecutor(T atom) {
        super(atom);
    }

    public abstract V calculate(BehaviourContext context);

    public abstract V calculateAndListenEvent(ExpressionRuntime<V> runtime);

    public void reset(ExpressionRuntime<V> runtime) {
        runtime.resetListeners();
    }
}
