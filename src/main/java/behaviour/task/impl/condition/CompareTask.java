package behaviour.task.impl.condition;

import behaviour.condition.Compare;
import behaviour.BehaviourFactory;
import behaviour.BehaviourContext;
import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.condition.CompareRuntime;
import behaviour.task.ConditionTask;
import behaviour.task.ExpressionNumberTask;

/**
 * Compare 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 姣旇緝涓や釜鏁板瓧琛ㄨ揪寮忕殑澶у皬鍏崇郴
 */
public class CompareTask extends ConditionTask implements CompareRuntime {

    private ExpressionNumberTask leftExprTask;
    private ExpressionNumberTask rightExprTask;

    public CompareTask(Compare atom, ConditionTask parentConditionTask, BehaviourContext context) {
        super(atom, parentConditionTask, context);
    }

    public void setLeftExprTask(ExpressionNumberTask leftExprTask) {
        this.leftExprTask = leftExprTask;
    }

    public void setRightExprTask(ExpressionNumberTask rightExprTask) {
        this.rightExprTask = rightExprTask;
    }

    public ExpressionNumberTask getLeftExprTask() {
        return leftExprTask;
    }

    public ExpressionNumberTask getRightExprTask() {
        return rightExprTask;
    }

    @Override
    public ExpressionRuntime<TypedNumber> getLeftExprRuntime() {
        return leftExprTask;
    }

    @Override
    public ExpressionRuntime<TypedNumber> getRightExprRuntime() {
        return rightExprTask;
    }

    @Override
    public Compare getAtom() {
        return (Compare) super.getAtom();
    }

    @Override
    public void init() {
        @SuppressWarnings({"unchecked", "rawtypes"})
        ExpressionNumberTask leftTask = (ExpressionNumberTask) BehaviourFactory.createExpressionRuntime(
                getAtom().getLeftExpr(), this, getContext());
        @SuppressWarnings({"unchecked", "rawtypes"})
        ExpressionNumberTask rightTask = (ExpressionNumberTask) BehaviourFactory.createExpressionRuntime(
                getAtom().getRightExpr(), this, getContext());
        leftExprTask = leftTask;
        rightExprTask = rightTask;
    }
}

