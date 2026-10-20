package behaviour.runtime.action.actions.move;

import behaviour.runtime.NodeRuntime;
import behaviour.BehaviourOperation;

public interface ActWalkToPosRuntime extends NodeRuntime {
    BehaviourOperation getMoveToPos();

    void setMoveToPos(BehaviourOperation moveToPos);
}
