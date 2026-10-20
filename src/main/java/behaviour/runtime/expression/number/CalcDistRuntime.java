package behaviour.runtime.expression.number;

import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;
import math.Vector3;

public interface CalcDistRuntime extends ExpressionRuntime<TypedNumber> {
    ExpressionRuntime<Vector3> getLeftRuntime();

    ExpressionRuntime<Vector3> getRightRuntime();
}
