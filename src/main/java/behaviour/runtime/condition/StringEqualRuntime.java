package behaviour.runtime.condition;

import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.ExpressionRuntime;

public interface StringEqualRuntime extends ConditionRuntime {
    ExpressionRuntime<String> getLeftExprStrRuntime();

    ExpressionRuntime<String> getRightExprStrRuntime();
}
