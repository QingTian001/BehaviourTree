package behaviour.task.impl.expression.string;

import behaviour.BehaviourContext;
import behaviour.task.ExpressionStringTask;
import behaviour.task.ConditionTask;
import behaviour.expression.string.Const;

/**
 * Const 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class ConstTask extends ExpressionStringTask {

    public ConstTask(Const atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public Const getAtom() {
        return (Const) super.getAtom();
    }

}