package behaviour.executor.condition;

import behaviour.BehaviourContext;
import behaviour.condition.ObjectEqual;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.condition.ObjectEqualRuntime;

public final class ObjectEqualExecutor extends ConditionExecutor<ObjectEqual> {
    public ObjectEqualExecutor(ObjectEqual atom) {
        super(atom);
    }

    @Override
    public boolean calculate(BehaviourContext context) {
        Object left = getAtom().getLeftObjExpr().calculate(context);
        Object right = getAtom().getRightObjExpr().calculate(context);
        return left == right;
    }

    @Override
    public boolean calculateAndListenEvent(ConditionRuntime runtime) {
        ObjectEqualRuntime objectEqualRuntime = (ObjectEqualRuntime) runtime;
        Object left = objectEqualRuntime.getLeftObjExprRuntime().calculateAndListenEvent();
        Object right = objectEqualRuntime.getRightObjExprRuntime().calculateAndListenEvent();
        return left == right;
    }

    @Override
    public void reset(ConditionRuntime runtime, boolean recursive) {
        super.reset(runtime, recursive);
        ObjectEqualRuntime objectEqualRuntime = (ObjectEqualRuntime) runtime;
        if (objectEqualRuntime.getLeftObjExprRuntime() != null) {
            objectEqualRuntime.getLeftObjExprRuntime().reset(recursive);
        }
        if (objectEqualRuntime.getRightObjExprRuntime() != null) {
            objectEqualRuntime.getRightObjExprRuntime().reset(recursive);
        }
    }
}
