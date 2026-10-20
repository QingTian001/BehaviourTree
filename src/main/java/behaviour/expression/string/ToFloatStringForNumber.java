package behaviour.expression.string;

import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionNumber;
import behaviour.expression.ExpressionString;

/**
 * Convert number to float string expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class ToFloatStringForNumber extends ExpressionString<config.behaviour.expstr.ToFloatStringForNumber> {
    private ExpressionNumber<?> numberExpr = null;

    public ToFloatStringForNumber(config.behaviour.expstr.ToFloatStringForNumber atomCfg) {
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
