package behaviour.expression.string;

import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionNumber;
import behaviour.expression.ExpressionString;

/**
 * Convert number to long string expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class ToLongStringForNumber extends ExpressionString<config.behaviour.expstr.ToLongStringForNumber> {
    private ExpressionNumber<?> numberExpr = null;

    public ToLongStringForNumber(config.behaviour.expstr.ToLongStringForNumber atomCfg) {
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
