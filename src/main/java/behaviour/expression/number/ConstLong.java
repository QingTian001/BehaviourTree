package behaviour.expression.number;
import behaviour.BehaviourHelper;
import behaviour.BehaviourTree;
import behaviour.condition.Condition;
import behaviour.expression.ExpressionNumber;

/**
 * Created by zyao on 2023/12/27 16:23
 */
public class ConstLong extends ExpressionNumber<config.behaviour.expnumber.ConstLong> {
    @SuppressWarnings("unchecked")
    public ConstLong(BehaviourTree behaviourTree, config.behaviour.expnumber.ConstLong exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        super(behaviourTree, exprCfg, condition);
    }

    protected Double internalCalculateExpression() {
        return BehaviourHelper.convertToDouble(getExprCfg().getValue());
    }

    protected Double internalCalculateExpressionAndListenEvent() {
        return BehaviourHelper.convertToDouble(getExprCfg().getValue());
    }

}
