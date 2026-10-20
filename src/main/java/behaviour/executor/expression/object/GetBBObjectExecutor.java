package behaviour.executor.expression.object;

import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.object.GetBBObject;
import behaviour.runtime.ExpressionRuntime;
import behaviour.task.BehaviourTask;
import behaviour.runtime.expression.object.GetBBObjectRuntime;

public final class GetBBObjectExecutor extends ExpressionExecutor<GetBBObject, Object> {
    public GetBBObjectExecutor(GetBBObject atom) {
        super(atom);
    }

    @Override
    public Object calculate(BehaviourContext context) {
        return context.getBlackBoard().getValue(getAtom().getAtomCfg().getBbKey(), null);
    }

    @Override
    public Object calculateAndListenEvent(ExpressionRuntime<Object> runtime) {
        GetBBObjectRuntime bbRuntime = (GetBBObjectRuntime) runtime;
        return ((BehaviourTask<?>) runtime).getRuntimeListenerManager().getValueAndRegisterBlackBoardListener(
                runtime.getContext().getBlackBoard(),
                getAtom().getAtomCfg().getBbKey(),
                null,
                bbRuntime.getListener());
    }
}
