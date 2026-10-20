package behaviour.runtime.condition;

import behaviour.TypedNumber;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.ExpressionRuntime;

public interface CompareRuntime extends ConditionRuntime {
    ExpressionRuntime<TypedNumber> getLeftExprRuntime();

    ExpressionRuntime<TypedNumber> getRightExprRuntime();
}
