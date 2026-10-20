package behaviour.task.impl.expression.number;

import behaviour.BehaviourContext;
import behaviour.BlackBoard;
import behaviour.runtime.expression.number.GetBBNumberRuntime;
import behaviour.TypedNumber;
import behaviour.task.ExpressionNumberTask;
import behaviour.task.ConditionTask;
import behaviour.expression.number.GetBBNumber;

/**
 * GetBBNumber 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class GetBBNumberTask extends ExpressionNumberTask implements GetBBNumberRuntime {
    private final BlackBoard.IBBListener<?> listener =
            (bbKey, bbValue) -> {
                if (GetBBNumberTask .this.getCondition() != null) {
                    GetBBNumberTask.this.getCondition().setDirty();
                }
            };

    public GetBBNumberTask(GetBBNumber atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public GetBBNumber getAtom() {
        return (GetBBNumber) super.getAtom();
    }

    public final BlackBoard.IBBListener<?> getListener() {
        return listener;
    }
}
