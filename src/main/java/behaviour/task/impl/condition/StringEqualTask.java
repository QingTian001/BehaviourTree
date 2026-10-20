package behaviour.task.impl.condition;

import behaviour.condition.StringEqual;
import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.condition.StringEqualRuntime;
import behaviour.task.ConditionTask;
import behaviour.task.ExpressionStringTask;

/**
 * StringEqual 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 姣旇緝涓や釜瀛楃涓茶〃杈惧紡鏄惁鐩哥瓑锛堜娇鐢?equals锛?
 */
public class StringEqualTask extends ConditionTask implements StringEqualRuntime {

    private ExpressionStringTask leftExprStrTask;
    private ExpressionStringTask rightExprStrTask;

    public StringEqualTask(StringEqual atom, ConditionTask parentConditionTask, BehaviourContext context) {
        super(atom, parentConditionTask, context);
    }

    public void setLeftExprStrTask(ExpressionStringTask leftExprStrTask) {
        this.leftExprStrTask = leftExprStrTask;
    }

    public void setRightExprStrTask(ExpressionStringTask rightExprStrTask) {
        this.rightExprStrTask = rightExprStrTask;
    }

    public ExpressionStringTask getLeftExprStrTask() {
        return leftExprStrTask;
    }

    public ExpressionStringTask getRightExprStrTask() {
        return rightExprStrTask;
    }

    @Override
    public ExpressionRuntime<String> getLeftExprStrRuntime() {
        return leftExprStrTask;
    }

    @Override
    public ExpressionRuntime<String> getRightExprStrRuntime() {
        return rightExprStrTask;
    }

    @Override
    public StringEqual getAtom() {
        return (StringEqual) super.getAtom();
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void init() {
        ExpressionStringTask leftTask = (ExpressionStringTask) BehaviourFactory.createExpressionRuntime(
                getAtom().getLeftExprStr(), this, getContext());
        ExpressionStringTask rightTask = (ExpressionStringTask) BehaviourFactory.createExpressionRuntime(
                getAtom().getRightExprStr(), this, getContext());
        leftExprStrTask = leftTask;
        rightExprStrTask = rightTask;
    }
}

