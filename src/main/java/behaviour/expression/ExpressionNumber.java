package behaviour.expression;

import behaviour.BehaviourTree;
import config.behaviour.Condition;

/**
 * Created by zyao on 2023/12/20 17:52
 */
public abstract class ExpressionNumber<T extends config.behaviour.ExpNumber> extends Expression<T, Double>{
    @SuppressWarnings("unchecked")
    public ExpressionNumber(BehaviourTree behaviourTree, T exprCfg, behaviour.condition.Condition<? extends Condition> condition) {
        super(behaviourTree, exprCfg, condition);
    }
    @Override
    protected abstract Double internalCalculateExpression();

    @Override
    protected abstract Double internalCalculateExpressionAndListenEvent();

}
