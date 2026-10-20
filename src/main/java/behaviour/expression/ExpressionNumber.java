package behaviour.expression;

import behaviour.TypedNumber;

/**
 * Expression that returns a number (TypedNumber).
 */
public abstract class ExpressionNumber<T> extends Expression<T, TypedNumber> {

    public ExpressionNumber(T atomCfg) {
        super(atomCfg);
    }
}
