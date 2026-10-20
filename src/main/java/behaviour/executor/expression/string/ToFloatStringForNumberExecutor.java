package behaviour.executor.expression.string;

import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.string.ToFloatStringForNumber;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.string.ToFloatStringForNumberRuntime;

public final class ToFloatStringForNumberExecutor extends ExpressionExecutor<ToFloatStringForNumber, String> {
    public ToFloatStringForNumberExecutor(ToFloatStringForNumber atom) {
        super(atom);
    }

    @Override
    public String calculate(BehaviourContext context) {
        return String.valueOf(getAtom().getNumberExpr().calculate(context).floatValue());
    }

    @Override
    public String calculateAndListenEvent(ExpressionRuntime<String> runtime) {
        ToFloatStringForNumberRuntime numberRuntime = (ToFloatStringForNumberRuntime) runtime;
        return String.valueOf(numberRuntime.getNumberRuntime().calculateAndListenEvent().floatValue());
    }

    @Override
    public void reset(ExpressionRuntime<String> runtime) {
        super.reset(runtime);
        ToFloatStringForNumberRuntime numberRuntime = (ToFloatStringForNumberRuntime) runtime;
        if (numberRuntime.getNumberRuntime() != null) {
            numberRuntime.getNumberRuntime().reset(false);
        }
    }
}
