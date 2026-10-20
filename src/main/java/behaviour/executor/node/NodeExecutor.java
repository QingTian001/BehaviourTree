package behaviour.executor.node;

import config.behaviour.Statusenum;
import behaviour.BehaviourStack;
import behaviour.executor.Executor;
import behaviour.node.Node;
import behaviour.runtime.NodeRuntime;

public abstract class NodeExecutor<A extends Node<?>> extends Executor<A> {
    protected NodeExecutor(A atom) {
        super(atom);
    }

    public boolean onEnter(NodeRuntime runtime) {
        return true;
    }

    public abstract Statusenum update(NodeRuntime runtime, BehaviourStack stack);

    public Statusenum update(NodeRuntime runtime, BehaviourStack stack, Statusenum childStatus) {
        return update(runtime, stack);
    }

    public void onInterrupt(NodeRuntime runtime) {
    }

    public void reset(NodeRuntime runtime, boolean recursive) {
    }
}
