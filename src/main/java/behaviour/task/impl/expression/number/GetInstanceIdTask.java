package behaviour.task.impl.expression.number;

import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.number.GetInstanceIdRuntime;
import behaviour.task.ExpressionNumberTask;
import behaviour.task.ConditionTask;
import behaviour.expression.number.GetInstanceId;
import behaviour.task.ExpressionObjectTask;

/**
 * GetInstanceId 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 鐢?migrate_expressions.py 鑷姩鐢熸垚
 */
public class GetInstanceIdTask extends ExpressionNumberTask implements GetInstanceIdRuntime {


    private ExpressionObjectTask entityExpressionTask;
    public GetInstanceIdTask(GetInstanceId atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public GetInstanceId getAtom() {
        return (GetInstanceId) super.getAtom();
    }

    public void setEntityExpressionTask(ExpressionObjectTask entityExpressionTask) {
        this.entityExpressionTask = entityExpressionTask;
    }

    public ExpressionObjectTask getEntityExpressionTask() {
        return entityExpressionTask;
    }

    @Override
    public ExpressionRuntime<Object> getEntityRuntime() {
        return entityExpressionTask;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void init() {
        ExpressionObjectTask exprTask = (ExpressionObjectTask) BehaviourFactory.createExpressionRuntime(
                getAtom().getEntityExpression(), getCondition(), getContext());
        setEntityExpressionTask(exprTask);
    }

}

