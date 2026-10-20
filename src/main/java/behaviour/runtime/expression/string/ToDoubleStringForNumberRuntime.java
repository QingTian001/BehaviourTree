package behaviour.runtime.expression.string;

import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;

public interface ToDoubleStringForNumberRuntime extends ExpressionRuntime<String> {
    ExpressionRuntime<TypedNumber> getNumberRuntime();
}
