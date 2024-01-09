package behaviour.expression.number;

import behaviour.BehaviourTree;
import behaviour.condition.Condition;
import behaviour.expression.ExpressionNumber;


public class ConstLong extends ExpressionNumber<config.behaviour.expnumber.ConstLong> {
    @SuppressWarnings("unchecked")
    public ConstLong(BehaviourTree behaviourTree, config.behaviour.ExpNumber exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        super(behaviourTree, exprCfg, condition);
    }

    public double calculateExpressionNumber() {
        return getExprCfg().getValue();
    }

    public double calculateExpressionNumberAndListenEvent() {
        return getExprCfg().getValue();
    }

}
