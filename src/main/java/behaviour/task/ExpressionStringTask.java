package behaviour.task;

import behaviour.BehaviourContext;
import behaviour.expression.ExpressionString;

/**
 * 字符串表达式任务的基类
 */
public abstract class ExpressionStringTask extends ExpressionTask<String> {

    protected ExpressionStringTask(ExpressionString<? extends config.behaviour.expstr.ExpStr> atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }
}
