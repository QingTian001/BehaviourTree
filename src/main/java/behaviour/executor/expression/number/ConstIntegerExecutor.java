package behaviour.executor.expression.number;

import behaviour.BehaviourContext;
import behaviour.TypedNumber;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.runtime.ExpressionRuntime;

public final class ConstIntegerExecutor extends ExpressionExecutor<behaviour.expression.number.ConstInteger, TypedNumber> {
    public ConstIntegerExecutor(behaviour.expression.number.ConstInteger atom) {
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
