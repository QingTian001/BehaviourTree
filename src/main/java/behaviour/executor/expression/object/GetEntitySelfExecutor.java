package behaviour.executor.expression.object;

import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.object.GetEntitySelf;
import behaviour.runtime.ExpressionRuntime;

public final class GetEntitySelfExecutor extends ExpressionExecutor<GetEntitySelf, GEntity> {
    public GetEntitySelfExecutor(GetEntitySelf atom) {
        super(atom);
    }

    @Override
    public GEntity calculate(BehaviourContext context) {
        return context.getSelf();
    }

    @Override
    public GEntity calculateAndListenEvent(ExpressionRuntime<GEntity> runtime) {
        return runtime.getContext().getSelf();
    }
}
