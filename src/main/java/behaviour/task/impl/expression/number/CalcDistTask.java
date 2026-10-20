package behaviour.task.impl.expression.number;

import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.number.CalcDistRuntime;
import behaviour.task.ExpressionNumberTask;
import behaviour.task.ConditionTask;
import behaviour.expression.number.CalcDist;
import behaviour.task.ExpressionVector3Task;
import math.Vector3;

/**
 * CalcDist 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 鐢?migrate_expressions.py 鑷姩鐢熸垚
 */
public class CalcDistTask extends ExpressionNumberTask implements CalcDistRuntime {


    private ExpressionVector3Task leftExprTask;
    private ExpressionVector3Task rightExprTask;
    public CalcDistTask(CalcDist atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public CalcDist getAtom() {
        return (CalcDist) super.getAtom();
    }

    public void setLeftExprTask(ExpressionVector3Task leftExprTask) {
        this.leftExprTask = leftExprTask;
    }

    public void setRightExprTask(ExpressionVector3Task rightExprTask) {
        this.rightExprTask = rightExprTask;
    }

    public ExpressionVector3Task getLeftExprTask() {
        return leftExprTask;
    }

    public ExpressionVector3Task getRightExprTask() {
        return rightExprTask;
    }

    @Override
    public ExpressionRuntime<Vector3> getLeftRuntime() {
        return leftExprTask;
    }

    @Override
    public ExpressionRuntime<Vector3> getRightRuntime() {
        return rightExprTask;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void init() {
        leftExprTask = (ExpressionVector3Task) BehaviourFactory.createExpressionRuntime(
                getAtom().getLeftExpr(), getCondition(), getContext());
        rightExprTask = (ExpressionVector3Task) BehaviourFactory.createExpressionRuntime(
                getAtom().getRightExpr(), getCondition(), getContext());
    }

}

