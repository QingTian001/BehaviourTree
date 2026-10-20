package behaviour.runtime.expression.number;

import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;

public interface GetInstanceIdRuntime extends ExpressionRuntime<TypedNumber> {
    ExpressionRuntime<Object> getEntityRuntime();
}
