package behaviour.task.impl.condition;

import behaviour.condition.ObjectEqual;
import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.condition.ObjectEqualRuntime;
import behaviour.task.ConditionTask;
import behaviour.task.ExpressionObjectTask;

/**
 * ObjectEqual 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 姣旇緝涓や釜瀵硅薄琛ㄨ揪寮忔槸鍚︾浉绛夛紙浣跨敤 ==锛?
 */
public class ObjectEqualTask extends ConditionTask implements ObjectEqualRuntime {

    private ExpressionObjectTask leftObjExprTask;
    private ExpressionObjectTask rightObjExprTask;

    public ObjectEqualTask(ObjectEqual atom, ConditionTask parentConditionTask, BehaviourContext context) {
        super(atom, parentConditionTask, context);
    }

    public void setLeftObjExprTask(ExpressionObjectTask leftObjExprTask) {
        this.leftObjExprTask = leftObjExprTask;
    }

    public void setRightObjExprTask(ExpressionObjectTask rightObjExprTask) {
        this.rightObjExprTask = rightObjExprTask;
    }

    public ExpressionObjectTask getLeftObjExprTask() {
        return leftObjExprTask;
    }

    public ExpressionObjectTask getRightObjExprTask() {
        return rightObjExprTask;
    }

    @Override
    public ExpressionRuntime<Object> getLeftObjExprRuntime() {
        return leftObjExprTask;
    }

    @Override
    public ExpressionRuntime<Object> getRightObjExprRuntime() {
        return rightObjExprTask;
    }

    @Override
    public ObjectEqual getAtom() {
        return (ObjectEqual) super.getAtom();
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void init() {
        ExpressionObjectTask leftTask = (ExpressionObjectTask) BehaviourFactory.createExpressionRuntime(
                getAtom().getLeftObjExpr(), this, getContext());
        ExpressionObjectTask rightTask = (ExpressionObjectTask) BehaviourFactory.createExpressionRuntime(
                getAtom().getRightObjExpr(), this, getContext());
        leftObjExprTask = leftTask;
        rightObjExprTask = rightTask;
    }
}

