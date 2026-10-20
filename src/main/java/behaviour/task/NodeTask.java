package behaviour.task;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import behaviour.BehaviourContext;
import behaviour.BehaviourHelper;
import behaviour.BehaviourStack;
import behaviour.executor.node.NodeExecutor;
import behaviour.node.Node;
import behaviour.runtime.BehaviourTreeRuntime;
import behaviour.runtime.NodeRuntime;
import util.StringUtil;

/**
 * Base class for Node runtime tasks.
 * Holds status and delegates execution logic to Atom (Flyweight).
 */
public abstract class NodeTask extends BehaviourTask<Node<?>> implements NodeRuntime {
    private Statusenum status = Statusenum.BTINVALID;

    protected NodeTask(Node<?> atom, BehaviourTask<?> parent, BehaviourContext context) {
        super(atom, parent, context);
    }

    public final Statusenum getStatus() {
        return status;
    }

    protected final void setStatus(Statusenum status) {
        this.status = status;
    }

    /**
     * Update node - delegates to Atom's update method
     * Execution flow: enter → internalUpdate → finish
     */
    public final Statusenum update(BehaviourStack stack) {
        if (this.getBehaviourTreeTask().canPrintLog()) {
            log(Level.TRACE, StringUtil.format("update node."));
        }
        if (BehaviourHelper.isNodeFinished(status)) {
            throw new RuntimeException("error status:" + status);
        }

        // First entry
        if (this.status == Statusenum.BTINVALID) {
            stack.pushRunningNode(this);
            boolean ret = enter();
            if (!ret) {
                status = Statusenum.BTFAILURE;
            }
        }

        // Execute update logic via Executor
        if (status != Statusenum.BTFAILURE) {
            NodeExecutor<?> executor = requireExecutor(NodeExecutor.class);
            status = executor.update(this, stack);
        }

        Statusenum tmpStatus = status;
        // 在 finish/reset 前记录节点终态，否则 reset 会把运行时状态恢复为 BTINVALID。
        getBehaviourTreeTask().traceNodeStatus(this, status, false);
        if (BehaviourHelper.isNodeFinished(status)) {
            finish(stack);
        }
        return tmpStatus;
    }

    /**
     * Update based on child status - delegates to Atom
     */
    public final Statusenum update(BehaviourStack stack, Statusenum childStatus) {
        if (this.getBehaviourTreeTask().canPrintLog()) {
            log(Level.TRACE, StringUtil.format("update by child status {}.", childStatus));
        }
        if (!BehaviourHelper.isNodeFinished(childStatus)) {
            throw new RuntimeException("error childStatus:" + childStatus);
        }

        if (this.status != Statusenum.BTRUNNING) {
            throw new RuntimeException("error status:" + status);
        }

        NodeExecutor<?> executor = requireExecutor(NodeExecutor.class);
        status = executor.update(this, stack, childStatus);
        Statusenum tmpStatus = status;
        // 父节点由子节点结果回溯更新时，同样在 reset 前记录本次可观察状态。
        getBehaviourTreeTask().traceNodeStatus(this, status, false);
        if (BehaviourHelper.isNodeFinished(status)) {
            finish(stack);
        }
        return tmpStatus;
    }

    private void finish(BehaviourStack stack) {
        if (this.getBehaviourTreeTask().canPrintLog()) {
            log(Level.DEBUG, StringUtil.format("finish status {}.", status));
        }
        reset(false);
        NodeTask popNode = stack.popRunningNode();
        if (popNode != this) {
            throw new RuntimeException("node not equal. popNode:" + popNode + ", this:" + this);
        }
    }

    /**
     * Reset node runtime state.
     * Delegates to Atom's reset method for logic, then resets local status.
     * This method is final - subclasses should override Atom's reset method instead.
     *
     * @param recursive if true, reset child tasks recursively
     */
    @Override
    public final void reset(boolean recursive) {
        NodeExecutor<?> executor = requireExecutor(NodeExecutor.class);
        executor.reset(this, recursive);
        disposeRuntimeListeners();
        // reset 不向客户端发送 Invalid，只清理服务端的去重基线。
        getBehaviourTreeTask().traceNodeReset(this);
        // Reset local status
        this.status = Statusenum.BTINVALID;
    }

    private boolean enter() {
        if (this.getBehaviourTreeTask().canPrintLog()) {
            log(Level.DEBUG, "enter.");
        }
        NodeExecutor<?> executor = requireExecutor(NodeExecutor.class);
        return executor.onEnter(this);
    }

    /**
     * Interrupt this node - delegates to Atom
     */
    public final void interrupt() {
        if (this.getBehaviourTreeTask().canPrintLog()) {
            log(Level.DEBUG, "interrupt.");
        }
        NodeExecutor<?> executor = requireExecutor(NodeExecutor.class);
        executor.onInterrupt(this);
    }

    @Override
    public NodeRuntime getParentRuntime() {
        BehaviourTask<?> parent = getParent();
        return parent instanceof NodeRuntime ? (NodeRuntime) parent : null;
    }

    @Override
    public BehaviourTreeRuntime getRootBehaviourTreeRuntime() {
        return getRootBehaviourTreeTask();
    }

    public BehaviourTreeTask getRootBehaviourTreeTask() {
        return getBehaviourTreeTask();
    }

    @Override
    public String getRunningInfo() {
        return status == Statusenum.BTRUNNING ? "(Running)" : "";
    }
}
