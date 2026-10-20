package behaviour;

import config.behaviour.Behaviour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.GEntity;
import behaviour.node.Node;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.NodeTask;
import behaviour.trace.BehaviourTraceContext;

public final class BehaviourTree {
    public static final Logger logger = LogManager.getLogger(BehaviourTree.class);

    private final Behaviour behaviourCfg;
    private final Node<? extends config.behaviour.node.Node> rootNode;

    public BehaviourTree(Behaviour behaviourCfg) {
        this.behaviourCfg = behaviourCfg;
        this.rootNode = BehaviourFactory.createNode(behaviourCfg.getNode());
        // 建树完成后为所有 Atom 分配树内路径与稳定 id
        NodePath.assignTree(this.rootNode);
    }

    public long getId() {
        return behaviourCfg.getId();
    }

    public Behaviour getBehaviourCfg() {
        return behaviourCfg;
    }

    public Node<? extends config.behaviour.node.Node> getRootNode() {
        return rootNode;
    }

    public BehaviourTreeTask createRuntime(GEntity entity, BehaviourHost host) {
        return createRuntime(entity, host, new BehaviourTraceContext(), 0, 0);
    }

    /**
     * 创建树运行时；子树通过 parent 字段保留跨树关联，Trace context 由主树和子树共享。
     */
    public BehaviourTreeTask createRuntime(GEntity entity, BehaviourHost host, BehaviourTraceContext traceContext,
                                           long parentTreeInstanceId, long parentNodeId) {
        BehaviourTreeTask task = new BehaviourTreeTask(this, entity, host, traceContext,
                parentTreeInstanceId, parentNodeId);
        logger.info("Creating behaviour tree runtime for entity:{}, treeCfgId:{}, treeTaskInstId:{}, parrentInstanceId:{}, parentId:{}", entity.getInstanceId(), behaviourCfg.getId(), task.getInstanceId(), parentTreeInstanceId, parentNodeId);
        NodeTask rootTask = BehaviourFactory.createNodeRuntime(rootNode, null, task.getContext());
        task.initRootNodeTask(rootTask);
        return task;
    }

    public String dump(boolean withCfg) {
        StringBuilder sb = new StringBuilder();
        dump(0, sb, withCfg);
        return sb.toString();
    }

    public void dump(int tableNum, StringBuilder dumpTo, boolean withCfg) {
        util.StringUtil stringUtil = new util.StringUtil();
        if (tableNum == 0) {
            dumpTo.append("BehaviourTree CfgId:").append(this.getBehaviourCfg().getId()).append("\n");
        } else {
            dumpTo.append(stringUtil.getTableString(tableNum)).append("sub BehaviourTree CfgId:").append(this.getBehaviourCfg().getId()).append("\n");
        }
        rootNode.dump(tableNum, dumpTo, withCfg);
    }
}
