package behaviour.executor.expression.string;

import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.string.GetBBStr;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.string.GetBBStrRuntime;
import behaviour.task.BehaviourTask;

public final class GetBBStrExecutor extends ExpressionExecutor<GetBBStr, String> {
    public GetBBStrExecutor(GetBBStr atom) {
        super(atom);
    }

    @Override
    public String calculate(BehaviourContext context) {
        return context.getBlackBoard().getValue(getAtom().getAtomCfg().getBbKey(), "");
    }

    @Override
    public String calculateAndListenEvent(ExpressionRuntime<String> runtime) {
        GetBBStrRuntime bbRuntime = (GetBBStrRuntime) runtime;
        return ((BehaviourTask<?>) runtime).getRuntimeListenerManager().getValueAndRegisterBlackBoardListener(
                runtime.getContext().getBlackBoard(),
                getAtom().getAtomCfg().getBbKey(),
                "",
                bbRuntime.getListener());
    }
}
