package behaviour.runtime.expression.string;

import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;

public interface ToIntStringForNumberRuntime extends ExpressionRuntime<String> {
    ExpressionRuntime<TypedNumber> getNumberRuntime();
}
