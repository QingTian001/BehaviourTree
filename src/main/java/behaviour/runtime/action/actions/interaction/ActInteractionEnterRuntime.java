package behaviour.runtime.action.actions.interaction;

import behaviour.GEntity;
import behaviour.runtime.NodeRuntime;
import behaviour.BehaviourOperation;

public interface ActInteractionEnterRuntime extends NodeRuntime {
    GEntity getTarget();

    void setTarget(GEntity target);

    long getAbilityCfgId();

    void setAbilityCfgId(long abilityCfgId);

    long getInteractionCfgId();

    void setInteractionCfgId(long interactionCfgId);

    BehaviourOperation getBehaviourOperation();

    void setBehaviourOperation(BehaviourOperation jobInteraction);
}
