package behaviour.expression.string;

import behaviour.expression.ExpressionString;

/**
 * Get blackboard string expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class GetBBStr extends ExpressionString<config.behaviour.expstr.GetBBStr> {


    public GetBBStr(config.behaviour.expstr.GetBBStr atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No child expressions to load
    }
}
