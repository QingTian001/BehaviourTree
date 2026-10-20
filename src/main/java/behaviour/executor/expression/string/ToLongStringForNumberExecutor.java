package behaviour.executor.expression.string;

import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.string.ToLongStringForNumber;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.string.ToLongStringForNumberRuntime;

public final class ToLongStringForNumberExecutor extends ExpressionExecutor<ToLongStringForNumber, String> {
    public ToLongStringForNumberExecutor(ToLongStringForNumber atom) {
        super(atom);
    }

    @Override
    public String calculate(BehaviourContext context) {
        return String.valueOf(getAtom().getNumberExpr().calculate(context).longValue());
    }

    @Override
    public String calculateAndListenEvent(ExpressionRuntime<String> runtime) {
        ToLongStringForNumberRuntime numberRuntime = (ToLongStringForNumberRuntime) runtime;
        return String.valueOf(numberRuntime.getNumberRuntime().calculateAndListenEvent().longValue());
    }

    @Override
    public void reset(ExpressionRuntime<String> runtime) {
        super.reset(runtime);
        ToLongStringForNumberRuntime numberRuntime = (ToLongStringForNumberRuntime) runtime;
        if (numberRuntime.getNumberRuntime() != null) {
            numberRuntime.getNumberRuntime().reset(false);
        }
    }
}
