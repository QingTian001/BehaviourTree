package behaviour.executor.expression.vector3;

import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.vector3.GetBBVector3;
import behaviour.runtime.ExpressionRuntime;
import behaviour.task.BehaviourTask;
import behaviour.runtime.expression.vector3.GetBBVector3Runtime;
import math.Vector3;

public final class GetBBVector3Executor extends ExpressionExecutor<GetBBVector3, Vector3> {
    public GetBBVector3Executor(GetBBVector3 atom) {
        super(atom);
    }

    @Override
    public Vector3 calculate(BehaviourContext context) {
        return context.getBlackBoard().getValue(getAtom().getAtomCfg().getBbKey(), null);
    }

    @Override
    public Vector3 calculateAndListenEvent(ExpressionRuntime<Vector3> runtime) {
        GetBBVector3Runtime bbRuntime = (GetBBVector3Runtime) runtime;
        return ((BehaviourTask<?>) runtime).getRuntimeListenerManager().getValueAndRegisterBlackBoardListener(
                runtime.getContext().getBlackBoard(),
                getAtom().getAtomCfg().getBbKey(),
                null,
                bbRuntime.getListener());
    }
}
