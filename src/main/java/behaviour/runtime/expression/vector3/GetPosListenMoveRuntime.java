package behaviour.runtime.expression.vector3;

import behaviour.GEntity;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.ListenerHandle;

public interface GetPosListenMoveRuntime extends ExpressionRuntime<math.Vector3> {
    ExpressionRuntime<GEntity> getEntityRuntime();

    ListenerHandle getMoveListener();

    void setMoveListener(ListenerHandle handle);

    boolean isListening();

    void setListening(boolean value);

    GEntity getListeningEntity();

    void setListeningEntity(GEntity entity);
}
