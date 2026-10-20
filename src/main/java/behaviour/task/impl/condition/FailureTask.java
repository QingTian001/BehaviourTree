package behaviour.task.impl.condition;

import behaviour.condition.Failure;
import behaviour.BehaviourContext;
import behaviour.task.ConditionTask;

/**
 * Failure 的运行时任务实现
 * 总是返回 false
 */
public class FailureTask extends ConditionTask {

    public FailureTask(Failure atom, ConditionTask parentConditionTask, BehaviourContext context) {
        super(atom, parentConditionTask, context);
    }

    @Override
    public Failure getAtom() {
        return (Failure) super.getAtom();
    }
}
