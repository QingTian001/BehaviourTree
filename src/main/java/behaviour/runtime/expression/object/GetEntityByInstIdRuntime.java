package behaviour.runtime.expression.object;

import behaviour.GEntity;
import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;

public interface GetEntityByInstIdRuntime extends ExpressionRuntime<GEntity> {
    ExpressionRuntime<TypedNumber> getInstIdRuntime();
}
