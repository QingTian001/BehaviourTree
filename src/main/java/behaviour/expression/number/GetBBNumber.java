package behaviour.expression.number;

import behaviour.expression.ExpressionNumber;

/**
 * Get blackboard number expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class GetBBNumber extends ExpressionNumber<config.behaviour.expnumber.GetBBNumber> {

    public GetBBNumber(config.behaviour.expnumber.GetBBNumber atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No child expressions to load
    }
}
