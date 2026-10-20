package behaviour.task.impl.expression.number;

import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.number.MaxOrMinRuntime;
import behaviour.task.ExpressionNumberTask;
import behaviour.task.ConditionTask;
import behaviour.expression.number.MaxOrMin;

/**
 * MaxOrMin 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 鐢?migrate_expressions.py 鑷姩鐢熸垚
 */
public class MaxOrMinTask extends ExpressionNumberTask implements MaxOrMinRuntime {

    // 瀛愯〃杈惧紡浠诲姟
    private ExpressionNumberTask leftTask;
    private ExpressionNumberTask rightTask;

    public MaxOrMinTask(MaxOrMin atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public MaxOrMin getAtom() {
        return (MaxOrMin) super.getAtom();
    }

    public void setLeftTask(ExpressionNumberTask leftTask) {
        this.leftTask = leftTask;
    }

    public void setRightTask(ExpressionNumberTask rightTask) {
        this.rightTask = rightTask;
    }

    public ExpressionNumberTask getLeftTask() {
        return leftTask;
    }

    public ExpressionNumberTask getRightTask() {
        return rightTask;
    }

    @Override
    public ExpressionRuntime<TypedNumber> getLeftRuntime() {
        return leftTask;
    }

    @Override
    public ExpressionRuntime<TypedNumber> getRightRuntime() {
        return rightTask;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void init() {
        ExpressionNumberTask leftExprTask = (ExpressionNumberTask) BehaviourFactory.createExpressionRuntime(
                getAtom().getLeftExpr(), getCondition(), getContext());
        ExpressionNumberTask rightExprTask = (ExpressionNumberTask) BehaviourFactory.createExpressionRuntime(
                getAtom().getRightExpr(), getCondition(), getContext());
        setLeftTask(leftExprTask);
        setRightTask(rightExprTask);
    }
}

