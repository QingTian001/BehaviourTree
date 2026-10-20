package behaviour.task;

import behaviour.BehaviourContext;
import behaviour.TypedNumber;
import behaviour.expression.ExpressionNumber;

/**
 * 数值表达式任务的基类
 */
public abstract class ExpressionNumberTask extends ExpressionTask<TypedNumber> {

    protected ExpressionNumberTask(ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }
}
