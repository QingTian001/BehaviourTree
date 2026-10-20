package behaviour.task.impl.expression.vector3;

import behaviour.BehaviourContext;
import behaviour.task.ExpressionVector3Task;
import behaviour.task.ConditionTask;
import math.Vector3;
import behaviour.expression.vector3.Const;

/**
 * Const 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class ConstTask extends ExpressionVector3Task {

    public ConstTask(Const atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public Const getAtom() {
        return (Const) super.getAtom();
    }

}