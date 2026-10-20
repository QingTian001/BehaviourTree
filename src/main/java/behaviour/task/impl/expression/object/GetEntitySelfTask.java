package behaviour.task.impl.expression.object;

import behaviour.BehaviourContext;
import behaviour.task.ExpressionObjectTask;
import behaviour.task.ConditionTask;
import behaviour.expression.object.GetEntitySelf;

/**
 * GetEntitySelf 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class GetEntitySelfTask extends ExpressionObjectTask {

    public GetEntitySelfTask(GetEntitySelf atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public GetEntitySelf getAtom() {
        return (GetEntitySelf) super.getAtom();
    }

}