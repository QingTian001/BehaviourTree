package behaviour.expression.number;

import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionNumber;
import behaviour.expression.object.ExpressionGEntity;

/**
 * Get instance ID expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class GetInstanceId extends ExpressionNumber<config.behaviour.expnumber.GetInstanceId> {
    private ExpressionGEntity<?> entityExpression;

    public GetInstanceId(config.behaviour.expnumber.GetInstanceId atomCfg) {
        super(atomCfg);
    }

    public ExpressionGEntity<?> getEntityExpression() {
        return entityExpression;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void loadFromCfg() {
        entityExpression = (ExpressionGEntity) BehaviourFactory.createExpression(getAtomCfg().getEntity());
    }
}
