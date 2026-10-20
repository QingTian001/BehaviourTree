package behaviour.task.impl.expression.object;

import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.object.GetEntityByInstIdRuntime;
import behaviour.task.ExpressionObjectTask;
import behaviour.task.ConditionTask;
import behaviour.expression.object.GetEntityByInstId;
import behaviour.task.ExpressionNumberTask;

/**
 * GetEntityByInstId 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 鐢?migrate_expressions.py 鑷姩鐢熸垚
 */
public class GetEntityByInstIdTask extends ExpressionObjectTask<GEntity> implements GetEntityByInstIdRuntime {


    private ExpressionNumberTask instIdExprTask;
    public GetEntityByInstIdTask(GetEntityByInstId atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public GetEntityByInstId getAtom() {
        return (GetEntityByInstId) super.getAtom();
    }

    public void setInstIdTask(ExpressionNumberTask instIdTask) {
        this.instIdExprTask = instIdTask;
    }

    public ExpressionNumberTask getInstIdTask() {
        return instIdExprTask;
    }

    @Override
    public ExpressionRuntime<TypedNumber> getInstIdRuntime() {
        return instIdExprTask;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void init() {
        ExpressionNumberTask exprTask = (ExpressionNumberTask) BehaviourFactory.createExpressionRuntime(
                getAtom().getInstIdExpr(), getCondition(), getContext());
        setInstIdTask(exprTask);
    }

}

