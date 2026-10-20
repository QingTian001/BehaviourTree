package behaviour.runtime.condition;

import behaviour.TypedNumber;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.ExpressionRuntime;

public interface CheckIsInInteractionRuntime extends ConditionRuntime {
    ExpressionRuntime<?> getEntityRuntime();

    ExpressionRuntime<TypedNumber> getAbilityIdRuntime();

    ExpressionRuntime<TypedNumber> getInteractionIdRuntime();
}
