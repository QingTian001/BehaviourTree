package behaviour.node.composite;

import behaviour.BehaviourFactory;
import behaviour.BehaviourTree;
import behaviour.node.Node;

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
}
