package behaviour.node;

import behaviour.GEntity;
import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.BehaviourHelper;
import behaviour.BehaviourStack;
import behaviour.BehaviourTree;
import util.StringUtil;


public abstract class Node<T extends config.behaviour.Node> {
    public static final Logger logger = LogManager.getLogger(Node.class);
    private Node<? extends config.behaviour.Node> parent;
    private final T nodeCfg;
    private final BehaviourTree behaviourTree;
    private Statusenum status = Statusenum.BTINVALID;
    private final NodeId nodeId = new NodeId(this);

    @SuppressWarnings("unchecked")
    public Node(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
        this.behaviourTree = behaviourTree;
        this.nodeCfg = (T)nodeCfg;
    }

    public abstract void loadFromCfg();

    public void setParent(Node<? extends config.behaviour.Node> parent) {
        this.parent = parent;
    }

    public Node<? extends config.behaviour.Node> getParent() {
        return parent;
    }

    public Statusenum getStatus() {
        return status;
    }

    public T getNodeCfg() {
        return nodeCfg;
    }

    public final NodeId getNodeId() {
        return nodeId;
    }

    public final GEntity getSelf() {
        return getBehaviourTree().getEntity();
    }

    public BehaviourTree getBehaviourTree() {
        return behaviourTree;
    }

    public void genNodeId() {
        nodeId.genNodeId();
    }

    public final String getFullId() {
        return getNodeId().getFullNodeIdName();
    }

    public final Statusenum update(BehaviourStack stack) {
        log(Level.TRACE, StringUtil.format("update node"));
        if (BehaviourHelper.isNodeFinished(status)) {
            throw new RuntimeException("error status:" + status);
        }

        if (this.status == Statusenum.BTINVALID) {
            stack.pushRunningNode(this);
            boolean ret = enter();
            if (!ret) {
                status = Statusenum.BTFAILURE;
            }
        }
        // 到这status可能是Invalid, Failure, Running
        if (status != Statusenum.BTFAILURE) {
            status = internalUpdate(stack);
        }
        Statusenum tmpStatus = status;
        if (BehaviourHelper.isNodeFinished(status)) {
            finish(stack);
        }
        return tmpStatus;
    }

    public final Statusenum update(BehaviourStack stack, Statusenum childStatus) {
        log(Level.TRACE, StringUtil.format("update by child status {}.", childStatus));
        if (!BehaviourHelper.isNodeFinished(childStatus)) {
            throw new RuntimeException("error childStatus:" + childStatus);
        }

        if (this.status != Statusenum.BTRUNNING) {
            throw new RuntimeException("error status:" + status);
        }

        status = internalUpdate(stack, childStatus);
        Statusenum tmpStatus = status;
        if (BehaviourHelper.isNodeFinished(status)) {
            finish(stack);
        }
        return tmpStatus;
    }

    protected abstract Statusenum internalUpdate(BehaviourStack stack);

    protected Statusenum internalUpdate(BehaviourStack stack, Statusenum childStatus) {
        return internalUpdate(stack);
    }

    private void finish(BehaviourStack stack) {
        log(Level.TRACE, StringUtil.format("finish status {}.", status));
        reset(false);
        Node<? extends config.behaviour.Node> popNode = stack.popRunningNode();
        if (popNode != this) {
            throw new RuntimeException("node not equal");
        }
    }

    protected boolean enter() {
        return true;
    }
    public void reset(boolean recursive) {
        this.status = Statusenum.BTINVALID;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public void log(Level level, String msg) {
        logger.log(level, msg + " node:{}, entity:{}, behaviour:{}",
                this.getFullId(), this.getSelf(), this.getBehaviourTree().getBehaviourCfg().getId());
    }

    public void dump(int tableNum, StringBuilder dumpTo) {
        String tableStr = StringUtil.getTableString(tableNum);
        dumpTo.append(StringUtil.format("{}{}{}\n", tableStr, this.getNodeId().getNodeIdName(), status == Statusenum.BTRUNNING? "(Running)" : ""));
    }

}
