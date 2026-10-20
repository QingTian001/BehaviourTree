package behaviour.executor.expression.object;

import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.TypedNumber;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.object.GetEntityByInstId;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.object.GetEntityByInstIdRuntime;

public final class GetEntityByInstIdExecutor extends ExpressionExecutor<GetEntityByInstId, GEntity> {
    public GetEntityByInstIdExecutor(GetEntityByInstId atom) {
        super(atom);
    }

    @Override
    public GEntity calculate(BehaviourContext context) {
        long instId = getAtom().getInstIdExpr().calculate(context).longValue();
        return context.getHost().getEntity(instId);
    }

    @Override
    public GEntity calculateAndListenEvent(ExpressionRuntime<GEntity> runtime) {
        GetEntityByInstIdRuntime entityRuntime = (GetEntityByInstIdRuntime) runtime;
        long instId = entityRuntime.getInstIdRuntime().calculateAndListenEvent().longValue();
        return runtime.getContext().getHost().getEntity(instId);
    }

    @Override
    public void reset(ExpressionRuntime<GEntity> runtime) {
        super.reset(runtime);
        GetEntityByInstIdRuntime entityRuntime = (GetEntityByInstIdRuntime) runtime;
        if (entityRuntime.getInstIdRuntime() != null) {
            entityRuntime.getInstIdRuntime().reset(false);
        }
    }
}
