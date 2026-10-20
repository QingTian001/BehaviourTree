package behaviour.executor.condition;

import config.behaviour.Compareenum;
import behaviour.BehaviourContext;
import behaviour.TypedNumber;
import behaviour.condition.Compare;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.condition.CompareRuntime;

public final class CompareExecutor extends ConditionExecutor<Compare> {
    public CompareExecutor(Compare atom) {
        super(atom);
    }

    @Override
    public boolean calculate(BehaviourContext context) {
        TypedNumber leftV = getAtom().getLeftExpr().calculate(context);
        TypedNumber rightV = getAtom().getRightExpr().calculate(context);
        return compare(leftV, rightV, getAtom().getComparator());
    }

    @Override
    public boolean calculateAndListenEvent(ConditionRuntime runtime) {
        CompareRuntime compareRuntime = (CompareRuntime) runtime;
        TypedNumber leftV = compareRuntime.getLeftExprRuntime().calculateAndListenEvent();
        TypedNumber rightV = compareRuntime.getRightExprRuntime().calculateAndListenEvent();
        return compare(leftV, rightV, getAtom().getComparator());
    }

    private boolean compare(TypedNumber leftV, TypedNumber rightV, Compareenum comparator) {
        int comp = leftV.compareTo(rightV);
        if (comparator == Compareenum.EQUAL) {
            return comp == 0;
        } else if (comparator == Compareenum.LESS) {
            return comp < 0;
        } else if (comparator == Compareenum.GREATER) {
            return comp > 0;
        } else if (comparator == Compareenum.LESSEQUAL) {
            return comp <= 0;
        } else if (comparator == Compareenum.GREATEREQUAL) {
            return comp >= 0;
        }
        throw new RuntimeException("unexpected comparator : " + comparator);
    }

    @Override
    public void reset(ConditionRuntime runtime, boolean recursive) {
        super.reset(runtime, recursive);
        CompareRuntime compareRuntime = (CompareRuntime) runtime;
        if (compareRuntime.getLeftExprRuntime() != null) {
            compareRuntime.getLeftExprRuntime().reset(recursive);
        }
        if (compareRuntime.getRightExprRuntime() != null) {
            compareRuntime.getRightExprRuntime().reset(recursive);
        }
    }
}
