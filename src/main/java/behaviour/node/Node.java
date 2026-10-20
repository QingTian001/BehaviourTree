package behaviour.node;

import config.behaviour.Statusenum;
import behaviour.Atom;
import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.BehaviourStack;
import behaviour.runtime.NodeRuntime;
import behaviour.task.BehaviourTask;
import behaviour.task.NodeTask;

/**
 * Node base class - Flyweight pattern.
 * Contains logic only, runtime state in NodeTask.
 */
public abstract class Node<T extends config.behaviour.node.Node> extends Atom<T> {

    public Node(T atomCfg) {
        super(atomCfg);
    }

    public Statusenum update(NodeTask task, BehaviourStack stack) {
        throw new RuntimeException("should not reach here");
    }

    /**
     * Update based on child status - for composites
     */
    public Statusenum update(NodeTask task, BehaviourStack stack, Statusenum childStatus) {
        throw new RuntimeException("should not reach here");
    }

    /**
     * Called when node is entered
     * @return true to continue, false to fail immediately
     */
    public boolean onEnter(NodeTask task) {
        return true;
    }

    /**
     * Called when node is interrupted
     */
    public void onInterrupt(NodeTask task) {
    }

    /**
     * Reset node runtime state. Logic is in Atom, state is in Task.
     * Subclasses can override to implement custom reset logic (e.g., Composite resets children).
     *
     * @param task the Task whose state should be reset
     * @param recursive if true, reset child tasks recursively
     */
    public void reset(NodeTask task, boolean recursive) {
        // Default: just reset status to INVALID
        // Task-specific state reset should be handled in Task's reset method
    }

    public <R extends NodeRuntime> R createRuntime(NodeTask parent, BehaviourContext context, Class<R> runtimeClass) {
        return BehaviourFactory.instantiateNodeRuntime(this, parent, context, runtimeClass);
    }

    @Override
    public NodeTask createRuntime(BehaviourTask<?> parent, BehaviourContext context) {
        return createRuntime((NodeTask) parent, context, NodeTask.class);
    }

}
