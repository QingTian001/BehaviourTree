package behaviour.runtime.expression.number;

import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;

public interface MaxOrMinRuntime extends ExpressionRuntime<TypedNumber> {
    ExpressionRuntime<TypedNumber> getLeftRuntime();

    ExpressionRuntime<TypedNumber> getRightRuntime();
}
