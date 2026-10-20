package behaviour.expression.number;

import behaviour.BehaviourFactory;
import behaviour.TypedNumber;
import behaviour.expression.ExpressionNumber;

@SuppressWarnings({"unchecked", "rawtypes"})

/**
 * Max expression - returns the maximum of two numbers.
 * Flyweight pattern - logic here, no runtime state.
 */
public class Max extends MaxOrMin<config.behaviour.expnumber.Max>{

    public Max(config.behaviour.expnumber.Max atomCfg) {
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
        return TypedNumber.max(leftValue, rightValue);
    }
}
