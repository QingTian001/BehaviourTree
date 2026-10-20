package behaviour.runtime.expression.vector3;

import behaviour.GEntity;
import behaviour.runtime.ExpressionRuntime;
import math.Vector3;

public interface GetPosRuntime extends ExpressionRuntime<Vector3> {
    ExpressionRuntime<GEntity> getEntityRuntime();
}
