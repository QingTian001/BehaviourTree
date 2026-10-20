package behaviour.task.impl.expression.string;

import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.string.ToStringForNumberRuntime;
import behaviour.task.ExpressionStringTask;
import behaviour.task.ConditionTask;
import behaviour.expression.string.ToStringForNumber;
import behaviour.task.ExpressionNumberTask;

/**
 * ToStringForNumber 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 鐢?migrate_expressions.py 鑷姩鐢熸垚
 */
public class ToStringForNumberTask extends ExpressionStringTask implements ToStringForNumberRuntime {

    // 瀛愯〃杈惧紡浠诲姟
    private ExpressionNumberTask numberTask;

    public ToStringForNumberTask(ToStringForNumber atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public ToStringForNumber getAtom() {
        return (ToStringForNumber) super.getAtom();
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

