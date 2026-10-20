package behaviour.expression.string;

import behaviour.BehaviourFactory;
import behaviour.TypedNumber;
import behaviour.expression.ExpressionNumber;
import behaviour.expression.ExpressionString;

/**
 * Convert number to string expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class ToStringForNumber extends ExpressionString<config.behaviour.expstr.ToStringForNumber> {
    private ExpressionNumber<?> numberExpr = null;

    public ToStringForNumber(config.behaviour.expstr.ToStringForNumber atomCfg) {
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
