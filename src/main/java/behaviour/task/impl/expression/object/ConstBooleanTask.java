package behaviour.task.impl.expression.object;

import behaviour.BehaviourContext;
import behaviour.task.ExpressionObjectTask;
import behaviour.task.ConditionTask;
import behaviour.expression.object.ConstBoolean;

/**
 * ConstBoolean 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class ConstBooleanTask extends ExpressionObjectTask {

    public ConstBooleanTask(ConstBoolean atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public ConstBoolean getAtom() {
        return (ConstBoolean) super.getAtom();
    }

}