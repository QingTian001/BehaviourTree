package behaviour.task.impl.expression.number;

import behaviour.BehaviourContext;
import behaviour.task.ExpressionNumberTask;
import behaviour.task.ConditionTask;
import behaviour.expression.number.GetCurrentTimeMills;

/**
 * GetCurrentTimeMills 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class GetCurrentTimeMillsTask extends ExpressionNumberTask {

    public GetCurrentTimeMillsTask(GetCurrentTimeMills atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public GetCurrentTimeMills getAtom() {
        return (GetCurrentTimeMills) super.getAtom();
    }

}