package behaviour.expression.number;
import behaviour.BehaviourHelper;
import behaviour.BehaviourTree;
import behaviour.condition.Condition;
import behaviour.expression.ExpressionNumber;

/**
 * Created by zyao on 2023/12/28 14:46
 */
public class ConstInteger extends ExpressionNumber<config.behaviour.expnumber.ConstInteger> {
    @SuppressWarnings("unchecked")
    public ConstInteger(BehaviourTree behaviourTree, config.behaviour.expnumber.ConstInteger exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        super(behaviourTree, exprCfg, condition);
    }

    protected Double internalCalculateExpression() {
        return BehaviourHelper.convertToDouble(getExprCfg().getValue());
    }

    protected Double internalCalculateExpressionAndListenEvent() {
        return BehaviourHelper.convertToDouble(getExprCfg().getValue());
    }

}
