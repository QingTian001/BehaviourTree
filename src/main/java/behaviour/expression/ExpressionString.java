package behaviour.expression;

import behaviour.BehaviourTree;
import behaviour.condition.Condition;


public abstract class ExpressionString<T extends config.behaviour.ExpStr> extends Expression<T> {
    @SuppressWarnings("unchecked")
    public ExpressionString(BehaviourTree behaviourTree, config.behaviour.ExpStr exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        super(behaviourTree, (T)exprCfg, condition);
    }

    public abstract String calculateExpressionString();

    public abstract String calculateExpressionStringAndListenEvent();

}
