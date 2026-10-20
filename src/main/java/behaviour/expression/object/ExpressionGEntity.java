package behaviour.expression.object;

import behaviour.GEntity;
import behaviour.expression.ExpressionObject;

/**
 * Expression that returns a GEntity.
 */
public abstract class ExpressionGEntity<T> extends ExpressionObject<T, GEntity> {
    public ExpressionGEntity(T atomCfg) {
        super(atomCfg);
    }
}
