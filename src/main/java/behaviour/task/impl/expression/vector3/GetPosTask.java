package behaviour.task.impl.expression.vector3;

import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.vector3.GetPosRuntime;
import behaviour.task.ExpressionVector3Task;
import behaviour.task.ConditionTask;
import math.Vector3;
import behaviour.expression.vector3.GetPos;
import behaviour.task.ExpressionObjectTask;

/**
 * GetPos 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 鐢?migrate_expressions.py 鑷姩鐢熸垚
 */
public class GetPosTask extends ExpressionVector3Task implements GetPosRuntime {

    // 瀛愯〃杈惧紡浠诲姟
    private ExpressionObjectTask entityTask;

    public GetPosTask(GetPos atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public GetPos getAtom() {
        return (GetPos) super.getAtom();
    }

    public void setEntityTask(ExpressionObjectTask entityTask) {
        this.entityTask = entityTask;
    }

    public ExpressionObjectTask getEntityExpressionTask() {
        return entityTask;
    }

    public void setEntityExpressionTask(ExpressionObjectTask entityTask) {
        this.entityTask = entityTask;
    }

    @Override
    public ExpressionRuntime<GEntity> getEntityRuntime() {
        return entityTask;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void init() {
        entityTask = (ExpressionObjectTask) BehaviourFactory.createExpressionRuntime(
                getAtom().getEntityExpression(), getCondition(), getContext());
    }

}

