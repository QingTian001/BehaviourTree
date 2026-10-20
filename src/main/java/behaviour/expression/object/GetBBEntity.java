package behaviour.expression.object;

import behaviour.GEntity;

/**
 * Get blackboard entity expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class GetBBEntity extends ExpressionGEntity<config.behaviour.expobj.GetBBEntity> {


    public GetBBEntity(config.behaviour.expobj.GetBBEntity atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No child expressions to load
    }
}
