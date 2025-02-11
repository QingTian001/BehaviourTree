package behaviour;

import config.behaviour.Behaviour;
import config.behaviour.Statusenum;
//import wm.Map.entity.behaviour.GEntity;
import behaviour.node.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public final class BehaviourTree {
    public static final Logger logger = LogManager.getLogger(BehaviourTree.class);
    private static AtomicLong idGen = new AtomicLong();
    private final long id;
    private final GEntity entity;
    private final Behaviour behaviourCfg;
    private Statusenum status = Statusenum.BTINVALID;
    private BlackBoard blackBoard = new BlackBoard(this);
    private Node<? extends config.behaviour.Node> rootNode = null;

    private BehaviourStack runningStack = new BehaviourStack();

    public BehaviourTree(GEntity entity, Behaviour behaviourCfg, boolean autoReset) {
        this.entity = entity;
        this.behaviourCfg = behaviourCfg;
        rootNode = BehaviourFactory.createNode(this, behaviourCfg.getNode());
        this.id = idGen.incrementAndGet();
        this.rootNode.genNodeId();
    }

    public BehaviourStack getBehaviourStack() {
        return runningStack;
    }

    public Node<? extends config.behaviour.Node> getRootNode() {
        return rootNode;
    }

    public GEntity getEntity() {
        return entity;
    }

    public long getId() {
        return id;
    }

    public Behaviour getBehaviourCfg() {
        return behaviourCfg;
    }

    public Statusenum getStatus() {
        return status;
    }

    public BlackBoard getBlackBoard() {
        return blackBoard;
    }

    public void initParams(List<Object> params) {
        for (int i = 0; i < behaviourCfg.getParams().size(); i++) {
            blackBoard.putValue(behaviourCfg.getParams().get(i).getBbKey(), params.get(i));
        }
    }

    public void finish() {
        logger.debug("finish behaviourTree. status:{}, entityInstId:{}, btCfgId:{}. ", status, entity, this.getBehaviourCfg().getId());
        reset(false);
    }

    public void clearBlackBoard() {
        blackBoard.reset();
    }

    public void reset(boolean recursive) {
        //blackBoard.reset(); // blackboard是不是不应被清理, 先不清理
        getBehaviourStack().reset();
        status = Statusenum.BTINVALID;

        if (recursive) {
            rootNode.reset(true);
        }
    }

    public Statusenum update() {
        updateNode();
        if (BehaviourHelper.isNodeFinished(status)) {
            finish();
        }
        return status;
    }

    private void updateNode() {
        if (status == Statusenum.BTRUNNING) {
            status = getBehaviourStack().updateRunningNode(null);
        } else {
            status = rootNode.update(getBehaviourStack());
        }
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();
        rootNode.dump(0, sb);
        String dump = sb.toString();
        logger.info("dump behaviourTree. entityInstId:{}, btCfgId:{}. \n{}", entity, this.getBehaviourCfg().getId(), dump);
        return dump;
    }
}

