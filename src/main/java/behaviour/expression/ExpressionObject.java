package behaviour.expression;

import behaviour.BehaviourTree;
import behaviour.condition.Condition;

/**
 * Created by zyao on 2023/12/21 10:30
 */
public abstract class ExpressionObject<T extends config.behaviour.ExpObj, OBJ> extends Expression<T, OBJ> {
    @SuppressWarnings("unchecked")
    public ExpressionObject(BehaviourTree behaviourTree, T exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        super(behaviourTree, exprCfg, condition);
    }

    @Override
    protected abstract OBJ internalCalculateExpression();

    @Override
    protected abstract OBJ internalCalculateExpressionAndListenEvent();

}
