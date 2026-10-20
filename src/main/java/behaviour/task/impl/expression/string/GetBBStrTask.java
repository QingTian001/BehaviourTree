package behaviour.task.impl.expression.string;

import behaviour.BehaviourContext;
import behaviour.BlackBoard;
import behaviour.runtime.expression.string.GetBBStrRuntime;
import behaviour.task.ExpressionStringTask;
import behaviour.task.ConditionTask;
import behaviour.expression.string.GetBBStr;

/**
 * GetBBStr 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class GetBBStrTask extends ExpressionStringTask implements GetBBStrRuntime {
    private final BlackBoard.IBBListener<String> listener =
            (bbKey, bbValue) -> GetBBStrTask.this.getCondition().setDirty();

    public GetBBStrTask(GetBBStr atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public GetBBStr getAtom() {
        return (GetBBStr) super.getAtom();
    }

    @Override
    public final BlackBoard.IBBListener<String> getListener() {
        return listener;
    }

}
