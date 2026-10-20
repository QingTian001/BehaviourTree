package behaviour.executor.condition;

import behaviour.BehaviourContext;
import behaviour.condition.StringEqual;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.condition.StringEqualRuntime;

public final class StringEqualExecutor extends ConditionExecutor<StringEqual> {
    public StringEqualExecutor(StringEqual atom) {
        super(atom);
    }

    @Override
    public boolean calculate(BehaviourContext context) {
        String left = getAtom().getLeftExprStr().calculate(context);
        String right = getAtom().getRightExprStr().calculate(context);
        return left.equals(right);
    }

    @Override
    public boolean calculateAndListenEvent(ConditionRuntime runtime) {
        StringEqualRuntime stringEqualRuntime = (StringEqualRuntime) runtime;
        String left = stringEqualRuntime.getLeftExprStrRuntime().calculateAndListenEvent();
        String right = stringEqualRuntime.getRightExprStrRuntime().calculateAndListenEvent();
        return left.equals(right);
    }

    @Override
    public void reset(ConditionRuntime runtime, boolean recursive) {
        super.reset(runtime, recursive);
        StringEqualRuntime stringEqualRuntime = (StringEqualRuntime) runtime;
        if (stringEqualRuntime.getLeftExprStrRuntime() != null) {
            stringEqualRuntime.getLeftExprStrRuntime().reset(recursive);
        }
        if (stringEqualRuntime.getRightExprStrRuntime() != null) {
            stringEqualRuntime.getRightExprStrRuntime().reset(recursive);
        }
    }
}
