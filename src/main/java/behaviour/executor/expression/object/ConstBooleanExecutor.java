package behaviour.executor.expression.object;

import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.runtime.ExpressionRuntime;

public final class ConstBooleanExecutor extends ExpressionExecutor<behaviour.expression.object.ConstBoolean, Boolean> {
    public ConstBooleanExecutor(behaviour.expression.object.ConstBoolean atom) {
        super(atom);
    }

    @Override
    public Boolean calculate(BehaviourContext context) {
        return getAtom().getAtomCfg().getValue();
    }

    @Override
    public Boolean calculateAndListenEvent(ExpressionRuntime<Boolean> runtime) {
        return calculate(runtime.getContext());
    }
}
