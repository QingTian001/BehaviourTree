package behaviour.runtime.expression.number;

import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;

public interface CalculateRuntime extends ExpressionRuntime<TypedNumber> {
    ExpressionRuntime<TypedNumber> getLeftRuntime();

    ExpressionRuntime<TypedNumber> getRightRuntime();
}
