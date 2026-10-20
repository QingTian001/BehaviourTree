package behaviour.task.impl.condition;

import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.condition.Condition;
import behaviour.task.ConditionTask;
import behaviour.condition.Not;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.condition.NotRuntime;

/**
 * Not 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 瀵规潯浠跺彇鍙?
 */
public class NotTask extends ConditionTask implements NotRuntime {
    private ConditionTask condTask;

    public NotTask(Not atom, ConditionTask parentConditionTask, BehaviourContext context) {
        super(atom, parentConditionTask, context);
    }

    @Override
    public Not getAtom() {
        return (Not) super.getAtom();
    }

    @Override
    public void init() {
        Condition<?> cond = getAtom().getCond();
        condTask = BehaviourFactory.createConditionRuntime(cond, this, getContext());
    }

    public void setCondTask(ConditionTask task) {
        this.condTask = task;
    }

    public ConditionTask getCondTask() {
        return condTask;
    }

    @Override
    public ConditionRuntime getCondRuntime() {
        return condTask;
    }

    @Override
    public void clearDirty() {
        super.clearDirty();
        if (condTask != null) {
            condTask.clearDirty();
        }
    }
}

