package behaviour.task;

import behaviour.BehaviourContext;
import behaviour.expression.ExpressionVector3;
import math.Vector3;

/**
 * Vector3表达式任务的基类
 */
public abstract class ExpressionVector3Task extends ExpressionTask<Vector3> {

    protected ExpressionVector3Task(ExpressionVector3<? extends config.behaviour.expvector3.ExpVector3> atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }
}
