package behaviour.expression.number;

import behaviour.BehaviourFactory;
import behaviour.TypedNumber;
import behaviour.expression.ExpressionNumber;

@SuppressWarnings({"unchecked", "rawtypes"})

/**
 * Min expression - returns the minimum of two numbers.
 * Flyweight pattern - logic here, no runtime state.
 */
public class Min extends MaxOrMin<config.behaviour.expnumber.Min>{

    public Min(config.behaviour.expnumber.Min atomCfg) {
        super(atomCfg);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void loadFromCfg() {
        leftExpr = (ExpressionNumber) BehaviourFactory.createExpression(getAtomCfg().getLeft());
        rightExpr = (ExpressionNumber) BehaviourFactory.createExpression(getAtomCfg().getRight());
    }

    @Override
    protected TypedNumber maxOrMin(TypedNumber leftValue, TypedNumber rightValue) {
        return TypedNumber.min(leftValue, rightValue);
    }
}
