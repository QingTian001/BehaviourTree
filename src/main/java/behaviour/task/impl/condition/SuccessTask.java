package behaviour.task.impl.condition;

import behaviour.condition.Success;
import behaviour.BehaviourContext;
import behaviour.task.ConditionTask;

/**
 * Success 的运行时任务实现
 * 总是返回 true
 */
public class SuccessTask extends ConditionTask {

    public SuccessTask(Success atom, ConditionTask parentConditionTask, BehaviourContext context) {
        super(atom, parentConditionTask, context);
    }

    @Override
    public Success getAtom() {
        return (Success) super.getAtom();
    }
}
