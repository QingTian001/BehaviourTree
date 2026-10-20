package behaviour.runtime.expression.string;

import behaviour.runtime.ExpressionRuntime;
import math.Vector3;

public interface ToStringForVector3Runtime extends ExpressionRuntime<String> {
    ExpressionRuntime<Vector3> getVector3Runtime();
}
