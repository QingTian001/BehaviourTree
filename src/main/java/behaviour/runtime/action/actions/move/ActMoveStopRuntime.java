package behaviour.runtime.action.actions.move;

import behaviour.GEntity;
import behaviour.runtime.NodeRuntime;

public interface ActMoveStopRuntime extends NodeRuntime {
    GEntity getTarget();

    void setTarget(GEntity target);
}
