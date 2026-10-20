package behaviour.runtime.action.actions.move;

import behaviour.GEntity;
import behaviour.runtime.NodeRuntime;

public interface ActFaceToEntityRuntime extends NodeRuntime {
    GEntity getTarget();

    void setTarget(GEntity target);

    long getFinishTimeMs();

    void setFinishTimeMs(long finishTimeMs);
}
