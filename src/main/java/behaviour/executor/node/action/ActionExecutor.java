package behaviour.executor.node.action;

import config.behaviour.Statusenum;
import behaviour.BehaviourStack;
import behaviour.executor.node.NodeExecutor;
import behaviour.node.action.Action;
import behaviour.runtime.NodeRuntime;

/**
 * Executor for Action nodes.
 * Kept in the executor hierarchy so the existing reflection-based factory can discover it.
 */
public abstract class ActionExecutor<A extends Action<?>> extends NodeExecutor<A> {
    protected ActionExecutor(A atom) {
        super(atom);
    }

    @Override
    public abstract boolean onEnter(NodeRuntime runtime);

    @Override
    public abstract Statusenum update(NodeRuntime runtime, BehaviourStack stack);

    @Override
    public abstract void onInterrupt(NodeRuntime runtime);

    @Override
    public abstract void reset(NodeRuntime runtime, boolean recursive);
}
