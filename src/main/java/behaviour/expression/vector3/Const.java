package behaviour.expression.vector3;

import behaviour.expression.ExpressionVector3;

/**
 * Constant Vector3 expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class Const extends ExpressionVector3<config.behaviour.expvector3.Const> {

    public Const(config.behaviour.expvector3.Const atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No child expressions to load
    }
}
