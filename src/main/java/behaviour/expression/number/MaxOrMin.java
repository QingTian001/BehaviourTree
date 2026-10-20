package behaviour.expression.number;

import behaviour.BehaviourFactory;
import behaviour.TypedNumber;
import behaviour.expression.ExpressionNumber;

/**
 * Base class for Max and Min expressions.
 * Flyweight pattern - logic here, no runtime state.
 */
public abstract class MaxOrMin<T extends config.behaviour.expnumber.ExpNumber> extends ExpressionNumber<T> {
    protected ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> leftExpr = null;
    protected ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> rightExpr = null;

    public MaxOrMin(T atomCfg) {
        super(atomCfg);
    }

    public ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> getLeftExpr() {
        return leftExpr;
    }

    public ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> getRightExpr() {
        return rightExpr;
    }

    protected abstract TypedNumber maxOrMin(TypedNumber leftValue, TypedNumber rightValue);

    @Override
    public abstract void loadFromCfg();
}
