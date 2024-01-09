package behaviour.expression;
import behaviour.BehaviourTree;
import behaviour.condition.Condition;

public abstract class ExpressionNumber<T extends config.behaviour.ExpNumber> extends Expression<T>{
    @SuppressWarnings("unchecked")
    public ExpressionNumber(BehaviourTree behaviourTree, config.behaviour.ExpNumber exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        super(behaviourTree, (T)exprCfg, condition);
    }

    public abstract double calculateExpressionNumber();

    public abstract double calculateExpressionNumberAndListenEvent();

}
