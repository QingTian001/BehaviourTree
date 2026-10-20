package behaviour.executor.expression.string;

import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.runtime.ExpressionRuntime;

public final class ConstExecutor extends ExpressionExecutor<behaviour.expression.string.Const, String> {
    public ConstExecutor(behaviour.expression.string.Const atom) {
        super(atom);
    }

    @Override
    public String calculate(BehaviourContext context) {
        return getAtom().getAtomCfg().getValue();
    }

    @Override
    public String calculateAndListenEvent(ExpressionRuntime<String> runtime) {
        return calculate(runtime.getContext());
    }
}
