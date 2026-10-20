package behaviour.executor.expression.vector3;

import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.vector3.GetPos;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.vector3.GetPosRuntime;
import math.Vector3;

public final class GetPosExecutor extends ExpressionExecutor<GetPos, Vector3> {
    public GetPosExecutor(GetPos atom) {
        super(atom);
    }

    @Override
    public Vector3 calculate(BehaviourContext context) {
        GEntity entity = getAtom().getEntityExpression().calculate(context);
        return entity.getPos().Clone();
    }

    @Override
    public Vector3 calculateAndListenEvent(ExpressionRuntime<Vector3> runtime) {
        GetPosRuntime getPosRuntime = (GetPosRuntime) runtime;
        GEntity entity = getPosRuntime.getEntityRuntime().calculateAndListenEvent();
        return entity.getPos().Clone();
    }

    @Override
    public void reset(ExpressionRuntime<Vector3> runtime) {
        super.reset(runtime);
        GetPosRuntime getPosRuntime = (GetPosRuntime) runtime;
        if (getPosRuntime.getEntityRuntime() != null) {
            getPosRuntime.getEntityRuntime().reset(false);
        }
    }
}
