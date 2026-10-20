package behaviour.task.impl.expression.object;

import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.BlackBoard;
import behaviour.runtime.expression.object.GetBBEntityRuntime;
import behaviour.task.ExpressionObjectTask;
import behaviour.task.ConditionTask;
import behaviour.expression.object.GetBBEntity;

/**
 * GetBBEntity 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class GetBBEntityTask extends ExpressionObjectTask<GEntity> implements GetBBEntityRuntime {

    private final BlackBoard.IBBListener<GEntity> listener =
            (bbKey, bbValue) -> GetBBEntityTask.this.getCondition().setDirty();

    public GetBBEntityTask(GetBBEntity atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public GetBBEntity getAtom() {
        return (GetBBEntity) super.getAtom();
    }

    @Override
    public final BlackBoard.IBBListener<GEntity> getListener() {
        return listener;
    }
}
