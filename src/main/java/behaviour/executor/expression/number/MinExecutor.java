package behaviour.executor.expression.number;

import behaviour.TypedNumber;
import behaviour.expression.number.Min;

public final class MinExecutor extends MaxOrMinExecutor<Min> {
    public MinExecutor(Min atom) {
        super(atom);
    }

    @Override
    protected TypedNumber apply(TypedNumber leftValue, TypedNumber rightValue) {
        return TypedNumber.min(leftValue, rightValue);
    }
}
