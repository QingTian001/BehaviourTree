package behaviour.expression.number;

import behaviour.expression.ExpressionNumber;

/**
 * Constant float expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class ConstFloat extends ExpressionNumber<config.behaviour.expnumber.ConstFloat> {

    public ConstFloat(config.behaviour.expnumber.ConstFloat atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No child expressions to load
    }
}
