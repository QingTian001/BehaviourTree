package behaviour.expression.number;

import behaviour.expression.ExpressionNumber;

/**
 * Get current time in milliseconds expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class GetCurrentTimeMills extends ExpressionNumber<config.behaviour.expnumber.GetCurrentTimeMills> {

    public GetCurrentTimeMills(config.behaviour.expnumber.GetCurrentTimeMills atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No child expressions to load
    }
}
