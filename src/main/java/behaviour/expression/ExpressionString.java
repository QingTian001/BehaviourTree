package behaviour.expression;

import behaviour.BehaviourTree;
import behaviour.condition.Condition;

/**
 * Created by zyao on 2023/12/21 10:30
 */
public abstract class ExpressionString<T extends config.behaviour.ExpStr> extends Expression<T, String> {
    @SuppressWarnings("unchecked")
    public ExpressionString(BehaviourTree behaviourTree, config.behaviour.ExpStr exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        super(behaviourTree, (T)exprCfg, condition);
    }

    @Override
    protected abstract String internalCalculateExpression();

    @Override
    protected abstract String internalCalculateExpressionAndListenEvent();

}
