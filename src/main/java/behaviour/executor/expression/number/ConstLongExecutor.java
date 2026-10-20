package behaviour.executor.expression.number;

import behaviour.BehaviourContext;
import behaviour.TypedNumber;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.runtime.ExpressionRuntime;

public final class ConstLongExecutor extends ExpressionExecutor<behaviour.expression.number.ConstLong, TypedNumber> {
    public ConstLongExecutor(behaviour.expression.number.ConstLong atom) {
        super(atom);
    }

    @Override
    public TypedNumber calculate(BehaviourContext context) {
        return TypedNumber.of(getAtom().getAtomCfg().getValue());
    }

    @Override
    public TypedNumber calculateAndListenEvent(ExpressionRuntime<TypedNumber> runtime) {
        return calculate(runtime.getContext());
    }
}
