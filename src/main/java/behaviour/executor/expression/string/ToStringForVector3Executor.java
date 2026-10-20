package behaviour.executor.expression.string;

import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.string.ToStringForVector3;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.string.ToStringForVector3Runtime;
import math.Vector3;

public final class ToStringForVector3Executor extends ExpressionExecutor<ToStringForVector3, String> {
    public ToStringForVector3Executor(ToStringForVector3 atom) {
        super(atom);
    }

    @Override
    public String calculate(BehaviourContext context) {
        Vector3 v3 = getAtom().getV3Expression().calculate(context);
        return v3.toString();
    }

    @Override
    public String calculateAndListenEvent(ExpressionRuntime<String> runtime) {
        ToStringForVector3Runtime vector3Runtime = (ToStringForVector3Runtime) runtime;
        return vector3Runtime.getVector3Runtime().calculateAndListenEvent().toString();
    }

    @Override
    public void reset(ExpressionRuntime<String> runtime) {
        super.reset(runtime);
        ToStringForVector3Runtime vector3Runtime = (ToStringForVector3Runtime) runtime;
        if (vector3Runtime.getVector3Runtime() != null) {
            vector3Runtime.getVector3Runtime().reset(false);
        }
    }
}
