package behaviour.expression.vector3;

import behaviour.GEntity;
import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionObject;
import behaviour.expression.ExpressionVector3;
import math.Vector3;

/**
 * Get position listening to move events expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class GetPosListenMove extends ExpressionVector3<config.behaviour.expvector3.GetPosListenMove> {
    private ExpressionObject<?, GEntity> entityExpression = null;

    public GetPosListenMove(config.behaviour.expvector3.GetPosListenMove atomCfg) {
        super(atomCfg);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void loadFromCfg() {
        entityExpression = (ExpressionObject) BehaviourFactory.createExpression(getAtomCfg().getEntity());
    }

    public ExpressionObject<?, GEntity> getEntityExpression() {
        return entityExpression;
    }
}
