package behaviour.condition.conditions;

import config.behaviour.expnumber.ExpNumber;
import config.behaviour.expobj.ExpObj;
import behaviour.BehaviourFactory;
import behaviour.condition.Condition;
import behaviour.expression.ExpressionNumber;
import behaviour.expression.object.ExpressionGEntity;

/**
 * 检查entity是否在交互中
 */
public class CheckIsInInteraction extends Condition<config.behaviour.condition.CheckIsInInteraction> {
    private ExpressionGEntity<? extends ExpObj> entityExpression;
    private ExpressionNumber<? extends ExpNumber> abilityIdExpression;
    private ExpressionNumber<? extends ExpNumber> interactionIdExpression;

    public CheckIsInInteraction(config.behaviour.condition.CheckIsInInteraction atomCfg) {
        super(atomCfg);
    }

    public ExpressionGEntity<? extends ExpObj> getEntityExpression() {
        return entityExpression;
    }

    public ExpressionNumber<? extends ExpNumber> getAbilityIdExpression() {
        return abilityIdExpression;
    }

    public ExpressionNumber<? extends ExpNumber> getInteractionIdExpression() {
        return interactionIdExpression;
    }

    @Override
    public void loadFromCfg() {
        entityExpression = BehaviourFactory.createExpression(getAtomCfg().getEntity());
        abilityIdExpression = BehaviourFactory.createExpression(getAtomCfg().getAbilityId());
        interactionIdExpression = BehaviourFactory.createExpression(getAtomCfg().getInteractionId());
    }
}
