package behaviour.node;

import util.StringUtil;

public class NodeId {
    private final Node<? extends config.behaviour.Node> node;
    public NodeId(Node<? extends config.behaviour.Node> node) {
        this.node = node;
    }
    private int id; // 节点id
    private int subIdGen = 0; // 节点子节点id生成器
    private String fullNodeIdName;
    private String nodeIdName;

    public int getId() {
        return id;
    }

    public int getSubIdGenAndIncrement() {
        return subIdGen++;
    }


    public String getFullNodeIdName() {
        return fullNodeIdName;
    }

    public String getNodeIdName() {
        return nodeIdName;
    }

    private String getIdName(String name) {
        return StringUtil.format("{}_{}", this.id, name);
    }

    public void genNodeId() {
        Node<? extends config.behaviour.Node> rootNode = node.getBehaviourTree().getRootNode();
        Node<? extends config.behaviour.Node> parent = node.getParent();
        if (rootNode == this.node) { // rootNode
            this.id = 0;
            this.nodeIdName = getIdName(node.getName());
            this.fullNodeIdName = this.nodeIdName;
        } else {
            this.id = parent.getNodeId().getSubIdGenAndIncrement();

            this.nodeIdName = getIdName(node.getName());
            this.fullNodeIdName = parent.getNodeId().getFullNodeIdName() + "/" + this.nodeIdName;
        }
    }
}

