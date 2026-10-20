package behaviour.task.impl.expression.number;

import behaviour.BehaviourContext;
import behaviour.task.ExpressionNumberTask;
import behaviour.task.ConditionTask;
import behaviour.expression.number.ConstLong;

/**
 * ConstLong 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class ConstLongTask extends ExpressionNumberTask {

    public ConstLongTask(ConstLong atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public ConstLong getAtom() {
        return (ConstLong) super.getAtom();
    }

}