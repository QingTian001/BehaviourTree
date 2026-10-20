package behaviour.runtime;

import java.util.List;

public interface CompositeRuntime extends NodeRuntime {
    List<NodeRuntime> getNodeTaskList();

    List<NodeRuntime> getCleanActionTaskList();

    List<NodeRuntime> getEnterActionTaskList();

    ConditionRuntime getPreCondTask();

    void setPreCondTask(ConditionRuntime task);

    int getCurRunningIndex();

    void setCurRunningIndex(int index);
}
