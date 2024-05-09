package behaviour.expression.number;
import behaviour.BehaviourHelper;
import behaviour.BehaviourTree;
import behaviour.condition.Condition;
import behaviour.expression.ExpressionNumber;

/**
 * Created by zyao on 2023/12/27 16:24
 */
public class ConstFloat extends ExpressionNumber<config.behaviour.expnumber.ConstFloat> {
    @SuppressWarnings("unchecked")
    public ConstFloat(BehaviourTree behaviourTree, config.behaviour.expnumber.ConstFloat exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        super(behaviourTree, exprCfg, condition);
    }

    protected Double internalCalculateExpression() {
        return BehaviourHelper.convertToDouble(getExprCfg().getValue());
    }

    protected Double internalCalculateExpressionAndListenEvent() {
        return BehaviourHelper.convertToDouble(getExprCfg().getValue());
    }

}
