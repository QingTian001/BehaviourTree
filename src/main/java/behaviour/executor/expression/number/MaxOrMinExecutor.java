package behaviour.executor.expression.number;

import behaviour.BehaviourContext;
import behaviour.TypedNumber;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.number.MaxOrMin;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.number.MaxOrMinRuntime;

public abstract class MaxOrMinExecutor<T extends MaxOrMin<? extends config.behaviour.expnumber.ExpNumber>>
        extends ExpressionExecutor<T, TypedNumber> {
    protected MaxOrMinExecutor(T atom) {
        super(atom);
    }

    @Override
    public TypedNumber calculate(BehaviourContext context) {
        TypedNumber leftValue = getAtom().getLeftExpr().calculate(context);
        TypedNumber rightValue = getAtom().getRightExpr().calculate(context);
        return apply(leftValue, rightValue);
    }

    @Override
    public TypedNumber calculateAndListenEvent(ExpressionRuntime<TypedNumber> runtime) {
        MaxOrMinRuntime maxOrMinRuntime = (MaxOrMinRuntime) runtime;
        TypedNumber leftValue = maxOrMinRuntime.getLeftRuntime().calculateAndListenEvent();
        TypedNumber rightValue = maxOrMinRuntime.getRightRuntime().calculateAndListenEvent();
        return apply(leftValue, rightValue);
    }

    protected abstract TypedNumber apply(TypedNumber leftValue, TypedNumber rightValue);

    @Override
    public void reset(ExpressionRuntime<TypedNumber> runtime) {
        super.reset(runtime);
        MaxOrMinRuntime maxOrMinRuntime = (MaxOrMinRuntime) runtime;
        if (maxOrMinRuntime.getLeftRuntime() != null) {
            maxOrMinRuntime.getLeftRuntime().reset(false);
        }
        if (maxOrMinRuntime.getRightRuntime() != null) {
            maxOrMinRuntime.getRightRuntime().reset(false);
        }
    }
}
