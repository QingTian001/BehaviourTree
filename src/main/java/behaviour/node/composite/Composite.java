package behaviour.node.composite;

import behaviour.BehaviourFactory;
import behaviour.BehaviourTree;
import behaviour.node.Node;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class Composite<T extends config.behaviour.Node> extends Node<T> {

    private List<Node<? extends config.behaviour.Node>> nodeList = new ArrayList<>();
    public Composite(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
        super(behaviourTree, nodeCfg);
    }

    @Override
    public void loadFromCfg() {
        List<? extends config.behaviour.Node> configNodeList = getConfigNodeList();

        for (var configNode : configNodeList) {
            Node<? extends config.behaviour.Node> node = BehaviourFactory.createNode(getBehaviourTree(), configNode);
            nodeList.add(node);
            node.setParent(this);
        }
        if (nodeList.isEmpty()) {
            throw new RuntimeException("node list is empty");
        }
    }

    public final List<Node<? extends config.behaviour.Node>> getNodeList() {
        return nodeList;
    }
    protected abstract List<? extends config.behaviour.Node> getConfigNodeList();

    @Override
    public void reset(boolean recursive) {
        super.reset(recursive);
        if (recursive) {
            for (var node : nodeList) {
                node.reset(recursive);
            }
        }
    }

    @Override
    public void genNodeId() {
        super.genNodeId();
        nodeList.forEach(Node::genNodeId);
    }

    @Override
    public void dump(int tableNum, StringBuilder dumpTo) {
        super.dump(tableNum, dumpTo);
        int subTableNum = tableNum + 1;
        String tableStr = StringUtil.getTableString(tableNum);
        dumpTo.append(tableStr).append("{\n");
        dumpTo.append(tableStr).append("nodes:\n");
        for (var node : nodeList) {
            node.dump(subTableNum, dumpTo);
        }
        dumpTo.append(tableStr).append("}\n");
    }
}
