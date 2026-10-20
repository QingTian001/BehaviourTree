package behaviour.runtime.expression.string;

import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;

public interface ToFloatStringForNumberRuntime extends ExpressionRuntime<String> {
    ExpressionRuntime<TypedNumber> getNumberRuntime();
}
