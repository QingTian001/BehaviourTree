package behaviour.executor.expression.object;

import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.object.ObjNull;
import behaviour.runtime.ExpressionRuntime;

public final class ObjNullExecutor extends ExpressionExecutor<ObjNull, GEntity> {
    public ObjNullExecutor(ObjNull atom) {
        super(atom);
    }

    @Override
    public GEntity calculate(BehaviourContext context) {
        return null;
    }

    @Override
    public GEntity calculateAndListenEvent(ExpressionRuntime<GEntity> runtime) {
        return null;
    }
}
