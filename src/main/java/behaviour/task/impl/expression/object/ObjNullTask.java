package behaviour.task.impl.expression.object;

import behaviour.BehaviourContext;
import behaviour.task.ExpressionObjectTask;
import behaviour.task.ConditionTask;
import behaviour.expression.object.ObjNull;

/**
 * ObjNull 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class ObjNullTask extends ExpressionObjectTask {

    public ObjNullTask(ObjNull atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public ObjNull getAtom() {
        return (ObjNull) super.getAtom();
    }

}