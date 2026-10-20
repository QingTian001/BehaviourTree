package behaviour.expression.string;

import behaviour.expression.ExpressionString;

/**
 * Constant string expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class Const extends ExpressionString<config.behaviour.expstr.Const> {

    public Const(config.behaviour.expstr.Const atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No child expressions to load
    }
}
