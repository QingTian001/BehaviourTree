package behaviour.runtime.action.actions.time;

import behaviour.runtime.NodeRuntime;

public interface ActWaitRandomTimeMsRuntime extends NodeRuntime {
    long getStartTimeMs();

    void setStartTimeMs(long startTimeMs);

    double getTimeMs();

    void setTimeMs(double timeMs);
}
