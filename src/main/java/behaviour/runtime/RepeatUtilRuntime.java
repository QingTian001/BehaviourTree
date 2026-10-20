package behaviour.runtime;

public interface RepeatUtilRuntime extends NodeRuntime {
    NodeRuntime getDecoratedNodeTask();

    void setDecoratedNodeTask(NodeRuntime runtime);
}
