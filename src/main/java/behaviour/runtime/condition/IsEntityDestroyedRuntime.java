package behaviour.runtime.condition;

import behaviour.GEntity;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.ListenerHandle;

import java.util.Map;

public interface IsEntityDestroyedRuntime extends ConditionRuntime {
    ExpressionRuntime<?> getEntityRuntime();

    GEntity getEntity();

    void setEntity(GEntity entity);

    Map<GEntity, ListenerHandle> getListeners();
}
