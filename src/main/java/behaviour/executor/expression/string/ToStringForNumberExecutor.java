package behaviour.executor.expression.string;

import behaviour.BehaviourContext;
import behaviour.TypedNumber;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.string.ToStringForNumber;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.string.ToStringForNumberRuntime;

public final class ToStringForNumberExecutor extends ExpressionExecutor<ToStringForNumber, String> {
    public ToStringForNumberExecutor(ToStringForNumber atom) {
        super(atom);
    }

    @Override
    public String calculate(BehaviourContext context) {
        TypedNumber result = getAtom().getNumberExpr().calculate(context);
        return result.toNumberString();
    }

    @Override
    public String calculateAndListenEvent(ExpressionRuntime<String> runtime) {
        ToStringForNumberRuntime numberRuntime = (ToStringForNumberRuntime) runtime;
        TypedNumber result = numberRuntime.getNumberRuntime().calculateAndListenEvent();
        return result.toNumberString();
    }

    @Override
    public void reset(ExpressionRuntime<String> runtime) {
        super.reset(runtime);
        ToStringForNumberRuntime numberRuntime = (ToStringForNumberRuntime) runtime;
        if (numberRuntime.getNumberRuntime() != null) {
            numberRuntime.getNumberRuntime().reset(false);
        }
    }
}
