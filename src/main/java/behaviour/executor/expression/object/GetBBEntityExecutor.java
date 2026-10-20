package behaviour.executor.expression.object;

import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.object.GetBBEntity;
import behaviour.runtime.ExpressionRuntime;
import behaviour.task.BehaviourTask;
import behaviour.runtime.expression.object.GetBBEntityRuntime;

public final class GetBBEntityExecutor extends ExpressionExecutor<GetBBEntity, GEntity> {
    public GetBBEntityExecutor(GetBBEntity atom) {
        super(atom);
    }

    @Override
    public GEntity calculate(BehaviourContext context) {
        return context.getBlackBoard().getValue(getAtom().getAtomCfg().getBbKey(), null);
    }

    @Override
    public GEntity calculateAndListenEvent(ExpressionRuntime<GEntity> runtime) {
        GetBBEntityRuntime bbRuntime = (GetBBEntityRuntime) runtime;
        return ((BehaviourTask<?>) runtime).getRuntimeListenerManager().getValueAndRegisterBlackBoardListener(
                runtime.getContext().getBlackBoard(),
                getAtom().getAtomCfg().getBbKey(),
                null,
                bbRuntime.getListener());
    }
}
