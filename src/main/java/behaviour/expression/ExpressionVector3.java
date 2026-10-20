package behaviour.expression;

import math.Vector3;

/**
 * Expression that returns a Vector3.
 * Flyweight pattern - logic here, no runtime state.
 */
public abstract class ExpressionVector3<T extends config.behaviour.expvector3.ExpVector3> extends Expression<T, Vector3> {

    public ExpressionVector3(T atomCfg) {
        super(atomCfg);
    }

}
