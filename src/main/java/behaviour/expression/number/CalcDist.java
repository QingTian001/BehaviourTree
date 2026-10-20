package behaviour.expression.number;

import behaviour.BehaviourFactory;
import behaviour.TypedNumber;
import behaviour.expression.ExpressionNumber;
import behaviour.expression.ExpressionVector3;

/**
 * Calculate distance between two Vector3 expressions.
 * Flyweight pattern - logic here, no runtime state.
 */
public class CalcDist extends ExpressionNumber<config.behaviour.expnumber.CalcDist> {
    private ExpressionVector3<?> leftExpr;
    private ExpressionVector3<?> rightExpr;

    public CalcDist(config.behaviour.expnumber.CalcDist atomCfg) {
        super(atomCfg);
    }

    public ExpressionVector3<?> getLeftExpr() {
        return leftExpr;
    }

    public ExpressionVector3<?> getRightExpr() {
        return rightExpr;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void loadFromCfg() {
        leftExpr = (ExpressionVector3) BehaviourFactory.createExpression(getAtomCfg().getLeft());
        rightExpr = (ExpressionVector3) BehaviourFactory.createExpression(getAtomCfg().getRight());
    }
}
