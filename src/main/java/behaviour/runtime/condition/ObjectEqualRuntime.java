package behaviour.runtime.condition;

import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.ExpressionRuntime;

public interface ObjectEqualRuntime extends ConditionRuntime {
    ExpressionRuntime<Object> getLeftObjExprRuntime();

    ExpressionRuntime<Object> getRightObjExprRuntime();
}
