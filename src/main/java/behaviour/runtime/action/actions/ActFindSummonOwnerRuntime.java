package behaviour.runtime.action.actions;

import behaviour.runtime.NodeRuntime;

public interface ActFindSummonOwnerRuntime extends NodeRuntime {
    void setOwnerInstId(long ownerInstId);

    long getOwnerInstId();
}
