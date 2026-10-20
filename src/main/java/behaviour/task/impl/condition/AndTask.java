package behaviour.task.impl.condition;

import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.task.ConditionTask;
import behaviour.condition.And;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.condition.AndRuntime;

/**
 * And 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 璁＄畻涓や釜鏉′欢鐨勪笌杩愮畻
 */
public class AndTask extends ConditionTask implements AndRuntime {
    private ConditionTask leftCondTask;
    private ConditionTask rightCondTask;

    public AndTask(And atom, ConditionTask parentConditionTask, BehaviourContext context) {
        super(atom, parentConditionTask, context);
    }

    @Override
    public And getAtom() {
        return (And) super.getAtom();
    }

    @Override
    public void init() {
        leftCondTask = BehaviourFactory.createConditionRuntime(getAtom().getLeftCond(), this, getContext());
        rightCondTask = BehaviourFactory.createConditionRuntime(getAtom().getRightCond(), this, getContext());
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

