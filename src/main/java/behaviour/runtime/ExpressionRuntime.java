package behaviour.runtime;

public interface ExpressionRuntime<V> extends RuntimeSupport {
    V calculate();

    V calculateAndListenEvent();

    ConditionRuntime getConditionRuntime();
}
