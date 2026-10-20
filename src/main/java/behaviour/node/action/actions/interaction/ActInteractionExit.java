package behaviour.node.action.actions.interaction;

import config.behaviour.expnumber.ExpNumber;
import config.behaviour.expobj.ExpObj;
import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionNumber;
import behaviour.expression.object.ExpressionGEntity;
import behaviour.node.action.Action;

public class ActInteractionExit extends Action<config.behaviour.node.ActInteractionExit> {
    private ExpressionGEntity<? extends ExpObj> entityExpression = null;
    private ExpressionNumber<? extends ExpNumber> interactionIdExpression = null;
    private ExpressionNumber<? extends ExpNumber> abilityIdExpression = null;
    private ExpressionNumber<? extends ExpNumber> dataInteractionIdExpression = null;

    public ActInteractionExit(config.behaviour.node.ActInteractionExit atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        entityExpression = BehaviourFactory.createExpression(this.getAtomCfg().getEntity());
        interactionIdExpression = BehaviourFactory.createExpression(this.getAtomCfg().getInteractionId());
        abilityIdExpression = BehaviourFactory.createExpression(this.getAtomCfg().getAbilityId());
        dataInteractionIdExpression = BehaviourFactory.createExpression(this.getAtomCfg().getDataInteractionId());
    }

    public ExpressionGEntity<? extends ExpObj> getEntityExpression() {
        return entityExpression;
    }

    public ExpressionNumber<? extends ExpNumber> getInteractionIdExpression() {
        return interactionIdExpression;
    }

    public ExpressionNumber<? extends ExpNumber> getAbilityIdExpression() {
        return abilityIdExpression;
    }

    public ExpressionNumber<? extends ExpNumber> getDataInteractionIdExpression() {
        return dataInteractionIdExpression;
    }
}