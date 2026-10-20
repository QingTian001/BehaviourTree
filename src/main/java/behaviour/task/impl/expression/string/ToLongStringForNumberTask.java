package behaviour.task.impl.expression.string;

import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.string.ToLongStringForNumberRuntime;
import behaviour.task.ExpressionStringTask;
import behaviour.task.ConditionTask;
import behaviour.expression.string.ToLongStringForNumber;
import behaviour.task.ExpressionNumberTask;

/**
 * ToLongStringForNumber 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 鐢?migrate_expressions.py 鑷姩鐢熸垚
 */
public class ToLongStringForNumberTask extends ExpressionStringTask implements ToLongStringForNumberRuntime {

    // 瀛愯〃杈惧紡浠诲姟
    private ExpressionNumberTask numberTask;

    public ToLongStringForNumberTask(ToLongStringForNumber atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public ToLongStringForNumber getAtom() {
        return (ToLongStringForNumber) super.getAtom();
    }

    public void setNumberTask(ExpressionNumberTask numberTask) {
        this.numberTask = numberTask;
    }

    public ExpressionNumberTask getNumberTask() {
        return numberTask;
    }

    @Override
    public ExpressionRuntime<TypedNumber> getNumberRuntime() {
        return numberTask;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void init() {
        ExpressionNumberTask exprTask = (ExpressionNumberTask) BehaviourFactory.createExpressionRuntime(
                getAtom().getNumberExpr(), getCondition(), getContext());
        setNumberTask(exprTask);
    }
}

