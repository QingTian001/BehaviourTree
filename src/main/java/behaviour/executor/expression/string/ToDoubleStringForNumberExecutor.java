package behaviour.executor.expression.string;

import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.string.ToDoubleStringForNumber;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.string.ToDoubleStringForNumberRuntime;

public final class ToDoubleStringForNumberExecutor extends ExpressionExecutor<ToDoubleStringForNumber, String> {
    public ToDoubleStringForNumberExecutor(ToDoubleStringForNumber atom) {
        super(atom);
    }

    @Override
    public String calculate(BehaviourContext context) {
        return String.valueOf(getAtom().getNumberExpr().calculate(context).doubleValue());
    }

    @Override
    public String calculateAndListenEvent(ExpressionRuntime<String> runtime) {
        ToDoubleStringForNumberRuntime numberRuntime = (ToDoubleStringForNumberRuntime) runtime;
        return String.valueOf(numberRuntime.getNumberRuntime().calculateAndListenEvent().doubleValue());
    }

    @Override
    public void reset(ExpressionRuntime<String> runtime) {
        super.reset(runtime);
        ToDoubleStringForNumberRuntime numberRuntime = (ToDoubleStringForNumberRuntime) runtime;
        if (numberRuntime.getNumberRuntime() != null) {
            numberRuntime.getNumberRuntime().reset(false);
        }
    }
}
