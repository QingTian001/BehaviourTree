package behaviour.expression.vector3;

import behaviour.GEntity;
import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionObject;
import behaviour.expression.ExpressionVector3;

/**
 * Get position expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class GetPos extends ExpressionVector3<config.behaviour.expvector3.GetPos> {

    private ExpressionObject<?, GEntity> entityExpression = null;

    public GetPos(config.behaviour.expvector3.GetPos atomCfg) {
        super(atomCfg);
    }

    public ExpressionObject<?, GEntity> getEntityExpression() {
        return entityExpression;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void loadFromCfg() {
        entityExpression = (ExpressionObject) BehaviourFactory.createExpression(getAtomCfg().getEntity());
    }
}
