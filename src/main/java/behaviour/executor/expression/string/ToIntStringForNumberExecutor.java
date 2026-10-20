package behaviour.executor.expression.string;

import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.string.ToIntStringForNumber;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.string.ToIntStringForNumberRuntime;

public final class ToIntStringForNumberExecutor extends ExpressionExecutor<ToIntStringForNumber, String> {
    public ToIntStringForNumberExecutor(ToIntStringForNumber atom) {
        super(atom);
    }

    @Override
    public String calculate(BehaviourContext context) {
        return String.valueOf(getAtom().getNumberExpr().calculate(context).intValue());
    }

    @Override
    public String calculateAndListenEvent(ExpressionRuntime<String> runtime) {
        ToIntStringForNumberRuntime numberRuntime = (ToIntStringForNumberRuntime) runtime;
        return String.valueOf(numberRuntime.getNumberRuntime().calculateAndListenEvent().intValue());
    }

    @Override
    public void reset(ExpressionRuntime<String> runtime) {
        super.reset(runtime);
        ToIntStringForNumberRuntime numberRuntime = (ToIntStringForNumberRuntime) runtime;
        if (numberRuntime.getNumberRuntime() != null) {
            numberRuntime.getNumberRuntime().reset(false);
        }
    }
}
