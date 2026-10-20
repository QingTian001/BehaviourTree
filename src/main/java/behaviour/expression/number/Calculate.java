package behaviour.expression.number;

import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionNumber;

/**
 * Calculate expression - performs arithmetic operations.
 * Flyweight pattern - logic here, no runtime state.
 */
public class Calculate extends ExpressionNumber<config.behaviour.expnumber.Calculate> {

    private ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> leftExpr = null;
    private ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> rightExpr = null;

    public Calculate(config.behaviour.expnumber.Calculate atomCfg) {
        super(atomCfg);
    }

    public ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> getLeftExpr() {
        return leftExpr;
    }

    public ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> getRightExpr() {
        return rightExpr;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void loadFromCfg() {
        leftExpr = (ExpressionNumber) BehaviourFactory.createExpression(getAtomCfg().getLeft());
        rightExpr = (ExpressionNumber) BehaviourFactory.createExpression(getAtomCfg().getRight());
    }
}
