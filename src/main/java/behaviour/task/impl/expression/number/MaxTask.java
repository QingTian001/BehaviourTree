package behaviour.task.impl.expression.number;

import behaviour.BehaviourContext;
import behaviour.task.ConditionTask;
import behaviour.expression.number.Max;

/**
 * Max 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class MaxTask extends MaxOrMinTask {

    public MaxTask(Max atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public Max getAtom() {
        return (Max) super.getAtom();
    }

}
