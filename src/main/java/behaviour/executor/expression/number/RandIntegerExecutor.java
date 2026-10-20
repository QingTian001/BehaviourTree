package behaviour.executor.expression.number;

import behaviour.BehaviourContext;
import behaviour.TypedNumber;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.number.RandInteger;
import behaviour.runtime.ExpressionRuntime;
import util.MathUtil;

public final class RandIntegerExecutor extends ExpressionExecutor<RandInteger, TypedNumber> {
    public RandIntegerExecutor(RandInteger atom) {
        super(atom);
    }

    @Override
    public TypedNumber calculate(BehaviourContext context) {
        return TypedNumber.of(MathUtil.randBetween(getAtom().getAtomCfg().getMin(), getAtom().getAtomCfg().getMax()));
    }

    @Override
    public TypedNumber calculateAndListenEvent(ExpressionRuntime<TypedNumber> runtime) {
        return calculate(runtime.getContext());
    }
}
