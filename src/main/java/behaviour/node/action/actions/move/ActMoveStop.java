package behaviour.node.action.actions.move;

import config.behaviour.expobj.ExpObj;
import behaviour.BehaviourFactory;
import behaviour.expression.object.ExpressionGEntity;
import behaviour.node.action.Action;

/**
 * Created by zyao on 2025/1/16 16:54
 */
public class ActMoveStop extends Action<config.behaviour.node.ActMoveStop> {
    private ExpressionGEntity<? extends ExpObj> targetExpr;

    public ActMoveStop(config.behaviour.node.ActMoveStop atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        targetExpr = BehaviourFactory.createExpression(getAtomCfg().getEntity());
    }

    public ExpressionGEntity<? extends ExpObj> getTargetExpr() {
        return targetExpr;
    }
}
