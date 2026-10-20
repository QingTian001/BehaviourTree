package behaviour.node.composite;

import behaviour.BehaviourFactory;
import behaviour.node.Node;

import java.util.ArrayList;
import java.util.List;

import static behaviour.BehaviourTree.logger;

/**
 * Composite base class - Flyweight pattern.
 * Static configuration only. Runtime state in CompositeTask.
 */
public abstract class Composite<T extends config.behaviour.node.Node> extends Node<T> {

    protected final List<Node<? extends config.behaviour.node.Node>> nodeConfigs = new ArrayList<>();
    protected final List<Node<? extends config.behaviour.node.Node>> cleanActionConfigs = new ArrayList<>();
    protected final List<Node<? extends config.behaviour.node.Node>> enterActionConfigs = new ArrayList<>();

    public Composite(T atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        List<? extends config.behaviour.node.Node> configNodeList = getConfigNodeList();
        for (var configNode : configNodeList) {
            nodeConfigs.add(BehaviourFactory.createNode(configNode));
        }
        if (nodeConfigs.isEmpty()) {
            logger.warn("No child nodes found for Composite: {}", getClass().getSimpleName());
        }

        List<? extends config.behaviour.node.Node> cleanCfgActList = getConfigCleanActionList();
        cleanCfgActList.forEach(cfgAct -> cleanActionConfigs.add(BehaviourFactory.createNode(cfgAct)));

        List<? extends config.behaviour.node.Node> enterCfgActList = getConfigEnterActionList();
        enterCfgActList.forEach(cfgAct -> enterActionConfigs.add(BehaviourFactory.createNode(cfgAct)));
    }

    public List<Node<? extends config.behaviour.node.Node>> getNodeConfigs() {
        return nodeConfigs;
    }

    public List<Node<? extends config.behaviour.node.Node>> getCleanActionConfigs() {
        return cleanActionConfigs;
    }

    public List<Node<? extends config.behaviour.node.Node>> getEnterActionConfigs() {
        return enterActionConfigs;
    }

    protected abstract List<? extends config.behaviour.node.Node> getConfigNodeList();

    protected abstract List<? extends config.behaviour.node.Node> getConfigCleanActionList();

    protected abstract List<? extends config.behaviour.node.Node> getConfigEnterActionList();
}
