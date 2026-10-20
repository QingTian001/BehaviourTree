package behaviour.expression.string;

import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionNumber;
import behaviour.expression.ExpressionString;

/**
 * Convert number to int string expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class ToIntStringForNumber extends ExpressionString<config.behaviour.expstr.ToIntStringForNumber> {
    private ExpressionNumber<?> numberExpr = null;

    public ToIntStringForNumber(config.behaviour.expstr.ToIntStringForNumber atomCfg) {
        super(atomCfg);
    }

    public ExpressionNumber<?> getNumberExpr() {
        return numberExpr;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void loadFromCfg() {
        numberExpr = (ExpressionNumber) BehaviourFactory.createExpression(getAtomCfg().getNum());
    }
}
