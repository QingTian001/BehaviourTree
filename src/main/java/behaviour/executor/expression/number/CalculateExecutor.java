package behaviour.executor.expression.number;

import config.behaviour.Calculatorenum;
import behaviour.BehaviourContext;
import behaviour.TypedNumber;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.number.Calculate;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.number.CalculateRuntime;

public final class CalculateExecutor extends ExpressionExecutor<Calculate, TypedNumber> {
    public CalculateExecutor(Calculate atom) {
        super(atom);
    }

    @Override
    public TypedNumber calculate(BehaviourContext context) {
        TypedNumber leftValue = getAtom().getLeftExpr().calculate(context);
        TypedNumber rightValue = getAtom().getRightExpr().calculate(context);
        return performCalculation(leftValue, rightValue);
    }

    @Override
    public TypedNumber calculateAndListenEvent(ExpressionRuntime<TypedNumber> runtime) {
        CalculateRuntime calculateRuntime = (CalculateRuntime) runtime;
        TypedNumber leftValue = calculateRuntime.getLeftRuntime().calculateAndListenEvent();
        TypedNumber rightValue = calculateRuntime.getRightRuntime().calculateAndListenEvent();
        return performCalculation(leftValue, rightValue);
    }

    private TypedNumber performCalculation(TypedNumber leftValue, TypedNumber rightValue) {
        if (getAtom().getAtomCfg().getCalculator() == Calculatorenum.PLUS.getId()) {
            return leftValue.add(rightValue);
        } else if (getAtom().getAtomCfg().getCalculator() == Calculatorenum.SUB.getId()) {
            return leftValue.subtract(rightValue);
        } else if (getAtom().getAtomCfg().getCalculator() == Calculatorenum.MULTI.getId()) {
            return leftValue.multiply(rightValue);
        } else if (getAtom().getAtomCfg().getCalculator() == Calculatorenum.DIVIDE.getId()) {
            return leftValue.divide(rightValue);
        }
        throw new RuntimeException("unknown calculator: " + getAtom().getAtomCfg().getCalculator());
    }

    @Override
    public void reset(ExpressionRuntime<TypedNumber> runtime) {
        super.reset(runtime);
        CalculateRuntime calculateRuntime = (CalculateRuntime) runtime;
        if (calculateRuntime.getLeftRuntime() != null) {
            calculateRuntime.getLeftRuntime().reset(false);
        }
        if (calculateRuntime.getRightRuntime() != null) {
            calculateRuntime.getRightRuntime().reset(false);
        }
    }
}
