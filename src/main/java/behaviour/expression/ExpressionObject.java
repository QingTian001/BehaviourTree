package behaviour.expression;

import behaviour.BehaviourTree;
import behaviour.condition.Condition;


public abstract class ExpressionObject<OBJ, T extends config.behaviour.ExpObj> extends Expression<T> {
    @SuppressWarnings("unchecked")
    public ExpressionObject(BehaviourTree behaviourTree, config.behaviour.ExpObj exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        super(behaviourTree, (T)exprCfg, condition);
    }

    public abstract OBJ calculateExpressionObject();

    public abstract OBJ calculateExpressionObjectAndListenEvent();

}
