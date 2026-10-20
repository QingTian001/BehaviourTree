package behaviour.executor.expression.number;

import behaviour.TypedNumber;
import behaviour.expression.number.Max;

public final class MaxExecutor extends MaxOrMinExecutor<Max> {
    public MaxExecutor(Max atom) {
        super(atom);
    }

    @Override
    protected TypedNumber apply(TypedNumber leftValue, TypedNumber rightValue) {
        return TypedNumber.max(leftValue, rightValue);
    }
}
