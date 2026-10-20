package behaviour.expression.number;

import behaviour.expression.ExpressionNumber;

/**
 * Constant long expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class ConstLong extends ExpressionNumber<config.behaviour.expnumber.ConstLong> {

    public ConstLong(config.behaviour.expnumber.ConstLong atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No child expressions to load
    }
}
