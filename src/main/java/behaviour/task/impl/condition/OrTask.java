package behaviour.task.impl.condition;

import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.condition.Condition;
import behaviour.task.ConditionTask;
import behaviour.condition.Or;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.condition.OrRuntime;

/**
 * Or 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 璁＄畻涓や釜鏉′欢鐨勬垨杩愮畻
 */
public class OrTask extends ConditionTask implements OrRuntime {
    private ConditionTask leftCondTask;
    private ConditionTask rightCondTask;

    public OrTask(Or atom, ConditionTask parentConditionTask, BehaviourContext context) {
        super(atom, parentConditionTask, context);
    }

    @Override
    public Or getAtom() {
        return (Or) super.getAtom();
    }

    @Override
    public void init() {
        Condition<?> left = getAtom().getLeftCond();
        Condition<?> right = getAtom().getRightCond();
        leftCondTask = BehaviourFactory.createConditionRuntime(left, this, getContext());
        rightCondTask = BehaviourFactory.createConditionRuntime(right, this, getContext());
    }

    public void setLeftCondTask(ConditionTask task) {
        this.leftCondTask = task;
    }

    public void setRightCondTask(ConditionTask task) {
        this.rightCondTask = task;
    }

    public ConditionTask getLeftCondTask() {
        return leftCondTask;
    }

    public ConditionTask getRightCondTask() {
        return rightCondTask;
    }

    @Override
    public ConditionRuntime getLeftCondRuntime() {
        return leftCondTask;
    }

    @Override
    public ConditionRuntime getRightCondRuntime() {
        return rightCondTask;
    }

    @Override
    public void clearDirty() {
        super.clearDirty();
        if (leftCondTask != null) {
            leftCondTask.clearDirty();
        }
        if (rightCondTask != null) {
            rightCondTask.clearDirty();
        }
    }
}

