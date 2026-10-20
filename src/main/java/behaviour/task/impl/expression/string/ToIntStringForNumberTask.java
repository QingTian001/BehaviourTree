package behaviour.task.impl.expression.string;

import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.string.ToIntStringForNumberRuntime;
import behaviour.task.ExpressionStringTask;
import behaviour.task.ConditionTask;
import behaviour.expression.string.ToIntStringForNumber;
import behaviour.task.ExpressionNumberTask;

/**
 * ToIntStringForNumber 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 鐢?migrate_expressions.py 鑷姩鐢熸垚
 */
public class ToIntStringForNumberTask extends ExpressionStringTask implements ToIntStringForNumberRuntime {

    // 瀛愯〃杈惧紡浠诲姟
    private ExpressionNumberTask numberTask;

    public ToIntStringForNumberTask(ToIntStringForNumber atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public ToIntStringForNumber getAtom() {
        return (ToIntStringForNumber) super.getAtom();
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

