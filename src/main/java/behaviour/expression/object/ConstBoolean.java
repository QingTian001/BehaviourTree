package behaviour.expression.object;

import behaviour.expression.ExpressionObject;

/**
 * Constant boolean expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class ConstBoolean extends ExpressionObject<config.behaviour.expobj.ConstBoolean, Boolean> {

    public ConstBoolean(config.behaviour.expobj.ConstBoolean atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No child expressions to load
    }
}
