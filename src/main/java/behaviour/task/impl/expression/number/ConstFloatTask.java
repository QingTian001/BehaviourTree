package behaviour.task.impl.expression.number;

import behaviour.BehaviourContext;
import behaviour.task.ExpressionNumberTask;
import behaviour.task.ConditionTask;
import behaviour.expression.number.ConstFloat;

/**
 * ConstFloat 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class ConstFloatTask extends ExpressionNumberTask {

    public ConstFloatTask(ConstFloat atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public ConstFloat getAtom() {
        return (ConstFloat) super.getAtom();
    }

}