package behaviour.condition;

import config.behaviour.Compareenum;
import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionNumber;

/**
 * Compare condition - Flyweight pattern.
 * Compares two numeric expressions.
 */
public class Compare extends Condition<config.behaviour.condition.Compare> {

    private ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> leftExpr = null;
    private ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> rightExpr = null;
    private Compareenum comparator = null;

    public Compare(config.behaviour.condition.Compare atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        leftExpr = BehaviourFactory.createExpression(getAtomCfg().getLeft());
        rightExpr = BehaviourFactory.createExpression(getAtomCfg().getRight());
        comparator = Compareenum.get(getAtomCfg().getComarator());
    }

    public ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> getLeftExpr() {
        return leftExpr;
    }

    public ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> getRightExpr() {
        return rightExpr;
    }

    public Compareenum getComparator() {
        return comparator;
    }
}
