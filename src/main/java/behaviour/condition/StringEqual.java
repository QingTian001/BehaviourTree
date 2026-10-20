package behaviour.condition;

import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionString;

/**
 * StringEqual condition - Flyweight pattern.
 * Checks if two string expressions are equal.
 */
public class StringEqual extends Condition<config.behaviour.condition.StrEqual> {

    private ExpressionString<? extends config.behaviour.expstr.ExpStr> leftExprStr = null;
    private ExpressionString<? extends config.behaviour.expstr.ExpStr> rightExprStr = null;

    public StringEqual(config.behaviour.condition.StrEqual atomCfg) {
        super(atomCfg);
    }

    public ExpressionString<? extends config.behaviour.expstr.ExpStr> getLeftExprStr() {
        return leftExprStr;
    }

    public ExpressionString<? extends config.behaviour.expstr.ExpStr> getRightExprStr() {
        return rightExprStr;
    }

    @Override
    public void loadFromCfg() {
        leftExprStr = BehaviourFactory.createExpression(getAtomCfg().getLeft());
        rightExprStr = BehaviourFactory.createExpression(getAtomCfg().getRight());
    }
}
