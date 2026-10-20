package behaviour.executor.expression.vector3;

import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.runtime.ExpressionRuntime;
import math.Vector3;

public final class ConstExecutor extends ExpressionExecutor<behaviour.expression.vector3.Const, Vector3> {
    public ConstExecutor(behaviour.expression.vector3.Const atom) {
        super(atom);
    }

    @Override
    public Vector3 calculate(BehaviourContext context) {
        return new Vector3(getAtom().getAtomCfg().getX(), getAtom().getAtomCfg().getY(), getAtom().getAtomCfg().getZ());
    }

    @Override
    public Vector3 calculateAndListenEvent(ExpressionRuntime<Vector3> runtime) {
        return calculate(runtime.getContext());
    }
}
