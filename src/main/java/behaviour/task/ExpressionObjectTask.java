package behaviour.task;

import behaviour.BehaviourContext;
import behaviour.expression.ExpressionObject;

/**
 * 对象表达式任务的基类
 */
public abstract class ExpressionObjectTask<T> extends ExpressionTask<T> {

    protected ExpressionObjectTask(ExpressionObject<? extends config.behaviour.expobj.ExpObj, T> atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }
}
