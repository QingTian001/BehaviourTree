package behaviour.condition.conditions;

import config.behaviour.expobj.ExpObj;
import behaviour.BehaviourFactory;
import behaviour.condition.Condition;
import behaviour.expression.object.ExpressionGEntity;

/**
 * Is entity destroyed condition.
 */
public class IsEntityDestroyed extends Condition<config.behaviour.condition.IsEntityDestroyed> {
    private ExpressionGEntity<? extends ExpObj> entityExpression;

    public IsEntityDestroyed(config.behaviour.condition.IsEntityDestroyed atomCfg) {
        super(atomCfg);
    }

    public ExpressionGEntity<? extends ExpObj> getEntityExpression() {
        return entityExpression;
    }

    @Override
    public void loadFromCfg() {
        entityExpression = BehaviourFactory.createExpression(getAtomCfg().getEntity());
    }
}
