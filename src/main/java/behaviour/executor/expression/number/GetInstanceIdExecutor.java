package behaviour.executor.expression.number;

import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.TypedNumber;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.number.GetInstanceId;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.number.GetInstanceIdRuntime;

public final class GetInstanceIdExecutor extends ExpressionExecutor<GetInstanceId, TypedNumber> {
    public GetInstanceIdExecutor(GetInstanceId atom) {
        super(atom);
    }

    @Override
    public TypedNumber calculate(BehaviourContext context) {
        GEntity entity = getAtom().getEntityExpression().calculate(context);
        return TypedNumber.of(entity.getInstanceId());
    }

    @Override
    public TypedNumber calculateAndListenEvent(ExpressionRuntime<TypedNumber> runtime) {
        GetInstanceIdRuntime instanceIdRuntime = (GetInstanceIdRuntime) runtime;
        GEntity entity = (GEntity) instanceIdRuntime.getEntityRuntime().calculateAndListenEvent();
        return TypedNumber.of(entity.getInstanceId());
    }

    @Override
    public void reset(ExpressionRuntime<TypedNumber> runtime) {
        super.reset(runtime);
        GetInstanceIdRuntime instanceIdRuntime = (GetInstanceIdRuntime) runtime;
        if (instanceIdRuntime.getEntityRuntime() != null) {
            instanceIdRuntime.getEntityRuntime().reset(false);
        }
    }
}
