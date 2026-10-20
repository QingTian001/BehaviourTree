package behaviour.expression.string;

import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionNumber;
import behaviour.expression.ExpressionString;

/**
 * Convert number to double string expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class ToDoubleStringForNumber extends ExpressionString<config.behaviour.expstr.ToDoubleStringForNumber> {
    private ExpressionNumber<?> numberExpr = null;

    public ToDoubleStringForNumber(config.behaviour.expstr.ToDoubleStringForNumber atomCfg) {
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
