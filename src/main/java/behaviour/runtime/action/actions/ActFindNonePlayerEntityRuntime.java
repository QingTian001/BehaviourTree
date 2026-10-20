package behaviour.runtime.action.actions;

import behaviour.runtime.NodeRuntime;

public interface ActFindNonePlayerEntityRuntime extends NodeRuntime {
    long getLastEnterTimestamp();

    void setLastEnterTimestamp(long lastEnterTimestamp);
}
