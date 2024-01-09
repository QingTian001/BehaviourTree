package behaviour.expression.number;
import behaviour.BehaviourTree;
import behaviour.condition.Condition;
import behaviour.expression.ExpressionNumber;


public class ConstFloat extends ExpressionNumber<config.behaviour.expnumber.ConstFloat> {
    @SuppressWarnings("unchecked")
    public ConstFloat(BehaviourTree behaviourTree, config.behaviour.ExpNumber exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        super(behaviourTree, exprCfg, condition);
    }

    public double calculateExpressionNumber() {
        return getExprCfg().getValue();
    }

    public double calculateExpressionNumberAndListenEvent() {
        return getExprCfg().getValue();
    }

}
