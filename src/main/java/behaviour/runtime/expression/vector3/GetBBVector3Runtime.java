package behaviour.runtime.expression.vector3;

import behaviour.BlackBoard;
import behaviour.runtime.ExpressionRuntime;
import math.Vector3;

public interface GetBBVector3Runtime extends ExpressionRuntime<Vector3> {
    BlackBoard.IBBListener<Vector3> getListener();
}
