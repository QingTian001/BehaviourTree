package behaviour.executor.expression.number;

import behaviour.BehaviourContext;
import behaviour.TypedNumber;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.number.CalcDist;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.number.CalcDistRuntime;
import math.Vector3;

public final class CalcDistExecutor extends ExpressionExecutor<CalcDist, TypedNumber> {
    public CalcDistExecutor(CalcDist atom) {
        super(atom);
    }

    @Override
    public TypedNumber calculate(BehaviourContext context) {
        Vector3 left = getAtom().getLeftExpr().calculate(context);
        Vector3 right = getAtom().getRightExpr().calculate(context);
        return TypedNumber.of(left.Dist(right));
    }

    @Override
    public TypedNumber calculateAndListenEvent(ExpressionRuntime<TypedNumber> runtime) {
        CalcDistRuntime calcDistRuntime = (CalcDistRuntime) runtime;
        Vector3 left = calcDistRuntime.getLeftRuntime().calculateAndListenEvent();
        Vector3 right = calcDistRuntime.getRightRuntime().calculateAndListenEvent();
        return TypedNumber.of(left.Dist(right));
    }

    @Override
    public void reset(ExpressionRuntime<TypedNumber> runtime) {
        super.reset(runtime);
        CalcDistRuntime calcDistRuntime = (CalcDistRuntime) runtime;
        if (calcDistRuntime.getLeftRuntime() != null) {
            calcDistRuntime.getLeftRuntime().reset(false);
        }
        if (calcDistRuntime.getRightRuntime() != null) {
            calcDistRuntime.getRightRuntime().reset(false);
        }
    }
}
