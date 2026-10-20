package behaviour.expression.number;

import behaviour.expression.ExpressionNumber;

/**
 * Constant integer expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class ConstInteger extends ExpressionNumber<config.behaviour.expnumber.ConstInteger> {

    public ConstInteger(config.behaviour.expnumber.ConstInteger atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No child expressions to load
    }
}
