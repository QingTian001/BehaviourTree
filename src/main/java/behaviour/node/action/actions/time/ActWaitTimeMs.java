package behaviour.node.action.actions.time;

import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionNumber;
import behaviour.node.action.Action;

/**
 * Created by zyao on 2023/12/21 18:08
 */
public class ActWaitTimeMs extends Action<config.behaviour.node.ActWaitTimeMs> {
    private ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> timeMsExpr = null;

    public ActWaitTimeMs(config.behaviour.node.ActWaitTimeMs atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        timeMsExpr = BehaviourFactory.createExpression(getAtomCfg().getTimeMs());
    }

    public ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> getTimeMsExpr() {
        return timeMsExpr;
    }
}
