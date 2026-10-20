package behaviour.expression.number;

import behaviour.expression.ExpressionNumber;

/**
 * Random integer expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class RandInteger extends ExpressionNumber<config.behaviour.expnumber.RandInteger> {

    public RandInteger(config.behaviour.expnumber.RandInteger atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No child expressions to load
    }
}
