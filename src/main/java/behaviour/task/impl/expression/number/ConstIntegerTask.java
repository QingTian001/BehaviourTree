package behaviour.task.impl.expression.number;

import behaviour.BehaviourContext;
import behaviour.task.ExpressionNumberTask;
import behaviour.task.ConditionTask;
import behaviour.expression.number.ConstInteger;

/**
 * ConstInteger 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class ConstIntegerTask extends ExpressionNumberTask {

    public ConstIntegerTask(ConstInteger atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public ConstInteger getAtom() {
        return (ConstInteger) super.getAtom();
    }

}