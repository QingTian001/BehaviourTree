package behaviour.executor.expression.vector3;

import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.vector3.GetPosListenMove;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.ListenerHandle;
import behaviour.runtime.expression.vector3.GetPosListenMoveRuntime;
import event.object.EventMove;
import math.Vector3;

public final class GetPosListenMoveExecutor extends ExpressionExecutor<GetPosListenMove, Vector3> {
    public GetPosListenMoveExecutor(GetPosListenMove atom) {
        super(atom);
    }

    @Override
    public Vector3 calculate(BehaviourContext context) {
        GEntity entity = getAtom().getEntityExpression().calculate(context);
        return entity.getPos().Clone();
    }

    @Override
    public Vector3 calculateAndListenEvent(ExpressionRuntime<Vector3> runtime) {
        GetPosListenMoveRuntime moveRuntime = (GetPosListenMoveRuntime) runtime;
        GEntity entity = moveRuntime.getEntityRuntime().calculateAndListenEvent();

        if (!moveRuntime.isListening() || moveRuntime.getListeningEntity() != entity) {
            ListenerHandle oldHandle = moveRuntime.getMoveListener();
            if (oldHandle != null) {
                oldHandle.dispose();
            }
            ListenerHandle handle = moveRuntime.listeners().listen(entity, EventMove.class, event -> moveRuntime.setDirty());
            moveRuntime.setMoveListener(handle);
            moveRuntime.setListeningEntity(entity);
            moveRuntime.setListening(true);
        }

        return entity.getPos().Clone();
    }

    @Override
    public void reset(ExpressionRuntime<Vector3> runtime) {
        super.reset(runtime);
        GetPosListenMoveRuntime moveRuntime = (GetPosListenMoveRuntime) runtime;
        moveRuntime.setMoveListener(null);
        moveRuntime.setListening(false);
        moveRuntime.setListeningEntity(null);
        ExpressionRuntime<?> entityRuntime = moveRuntime.getEntityRuntime();
        if (entityRuntime != null) {
            entityRuntime.reset(false);
        }
    }
}
