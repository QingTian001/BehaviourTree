package behaviour.node;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.BehaviourHelper;
import behaviour.BehaviourStack;
import behaviour.BehaviourTree;


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

    public BehaviourTree getBehaviourTree() {
        return behaviourTree;
    }

    public void genNodeId() {
        nodeId.genNodeId();
    }

    public BehaviourStack getBehaviourStack() {
        return null; // TODO
    }

    public Statusenum update(BehaviourStack stack) {

        logger.info("update node. node:{}, parent:{}, NodeCfg:{}", this, parent, nodeCfg);
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
            reset(false);
            Node<? extends config.behaviour.Node> popNode = stack.popRunningNode();
            if (popNode != this) {
                throw new RuntimeException("node not equal");
            }
        }

        return tmpStatus;
    }

    public Statusenum update(BehaviourStack stack, Statusenum childStatus) {

        logger.info("update node by Child. node:{}, parent:{}, NodeCfg:{}", this, parent, nodeCfg);
        if (!BehaviourHelper.isNodeFinished(childStatus)) {
            throw new RuntimeException("error childStatus:" + childStatus);
        }

        if (this.status != Statusenum.BTRUNNING) {
            throw new RuntimeException("error status:" + status);
        }

        status = internalUpdate(stack, childStatus);

        Statusenum tmpStatus = status;
        if (BehaviourHelper.isNodeFinished(status)) {
            reset(false);
            Node<? extends config.behaviour.Node> popNode = stack.popRunningNode();
            if (popNode != this) {
                throw new RuntimeException("node not equal");
            }
        }

        return tmpStatus;
    }

    protected abstract Statusenum internalUpdate(BehaviourStack stack);

    protected Statusenum internalUpdate(BehaviourStack stack, Statusenum childStatus) {
        return internalUpdate(stack);
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

}
