package behaviour.expression.vector3;

import behaviour.expression.ExpressionVector3;

/**
 * Get blackboard Vector3 expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class GetBBVector3 extends ExpressionVector3<config.behaviour.expvector3.GetBBVector3> {

    public GetBBVector3(config.behaviour.expvector3.GetBBVector3 atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No child expressions to load
    }
}
