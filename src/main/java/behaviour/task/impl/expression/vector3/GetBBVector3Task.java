package behaviour.task.impl.expression.vector3;

import behaviour.BehaviourContext;
import behaviour.BlackBoard;
import behaviour.runtime.expression.vector3.GetBBVector3Runtime;
import behaviour.task.ExpressionVector3Task;
import behaviour.task.ConditionTask;
import math.Vector3;
import behaviour.expression.vector3.GetBBVector3;

/**
 * GetBBVector3 的运行时任务实现
 * 由 migrate_expressions.py 自动生成
 */
public class GetBBVector3Task extends ExpressionVector3Task implements GetBBVector3Runtime {
    private final BlackBoard.IBBListener<Vector3> listener =
            (bbKey, bbValue) -> GetBBVector3Task.this.getCondition().setDirty();

    public GetBBVector3Task(GetBBVector3 atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public GetBBVector3 getAtom() {
        return (GetBBVector3) super.getAtom();
    }

    @Override
    public final BlackBoard.IBBListener<Vector3> getListener() {
        return listener;
    }

}
