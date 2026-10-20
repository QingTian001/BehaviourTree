package behaviour.runtime.composite;

import behaviour.runtime.CompositeRuntime;
import behaviour.runtime.NodeRuntime;

public interface CompIntSwitchCaseRuntime extends CompositeRuntime {
    int getCurRunningCaseNum();

    void setCurRunningCaseNum(int num);

    int getValue();

    void setValue(int value);

    boolean useDefaultCase();

    void setDefaultCaseTask(NodeRuntime runtime);

    NodeRuntime getDefaultCaseTask();
}
