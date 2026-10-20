package behaviour.task.impl.expression.object;

import behaviour.BehaviourContext;
import behaviour.BlackBoard;
import behaviour.runtime.expression.object.GetBBObjectRuntime;
import behaviour.task.ExpressionObjectTask;
import behaviour.task.ConditionTask;
import behaviour.expression.object.GetBBObject;

/**
 * GetBBObject 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class GetBBObjectTask extends ExpressionObjectTask<Object> implements GetBBObjectRuntime {

    private final BlackBoard.IBBListener<Object> listener =
            (bbKey, bbValue) -> GetBBObjectTask.this.getCondition().setDirty();

    public GetBBObjectTask(GetBBObject atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public GetBBObject getAtom() {
        return (GetBBObject) super.getAtom();
    }

    @Override
    public final BlackBoard.IBBListener<Object> getListener() {
        return listener;
    }
}
