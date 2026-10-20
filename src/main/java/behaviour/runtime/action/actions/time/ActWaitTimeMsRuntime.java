package behaviour.runtime.action.actions.time;

import behaviour.runtime.NodeRuntime;

public interface ActWaitTimeMsRuntime extends NodeRuntime {
    long getStartTimeMs();

    void setStartTimeMs(long startTimeMs);

    double getTimeMs();

    void setTimeMs(double timeMs);
}
