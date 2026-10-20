package behaviour.runtime;

public interface DecoratorRuntime extends NodeRuntime {
    NodeRuntime getDecoratedNodeRuntime();

    void setDecoratedNodeRuntime(NodeRuntime decoratedNodeRuntime);
}
