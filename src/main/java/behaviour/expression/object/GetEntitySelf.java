package behaviour.expression.object;

import behaviour.GEntity;

/**
 * Get entity self expression.
 */
public class GetEntitySelf extends ExpressionGEntity<config.behaviour.expobj.GetEntitySelf> {

    public GetEntitySelf(config.behaviour.expobj.GetEntitySelf atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No child expressions to load.
    }
}
