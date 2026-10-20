package behaviour.expression.object;

import behaviour.GEntity;

/**
 * Null object expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class ObjNull extends ExpressionGEntity<config.behaviour.expobj.ObjNull> {

    public ObjNull(config.behaviour.expobj.ObjNull atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No child expressions to load
    }
}
