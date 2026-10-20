package behaviour.task.impl.expression.number;

import behaviour.BehaviourContext;
import behaviour.task.ExpressionNumberTask;
import behaviour.task.ConditionTask;
import behaviour.expression.number.RandInteger;

/**
 * RandInteger 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class RandIntegerTask extends ExpressionNumberTask {

    public RandIntegerTask(RandInteger atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public RandInteger getAtom() {
        return (RandInteger) super.getAtom();
    }

}