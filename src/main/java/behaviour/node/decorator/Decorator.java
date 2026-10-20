package behaviour.node.decorator;

import behaviour.BehaviourFactory;
import behaviour.node.Node;

/**
 * 装饰节点定义基类，仅负责单子节点结构。
 */
public abstract class Decorator<T extends config.behaviour.node.Node> extends Node<T> {

    private Node<? extends config.behaviour.node.Node> decoratedNode;

    public Decorator(T atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        decoratedNode = BehaviourFactory.createNode(getDecorateNodeCfg());
    }

    public final Node<? extends config.behaviour.node.Node> getDecoratedNode() {
        return decoratedNode;
    }

    protected abstract config.behaviour.node.Node getDecorateNodeCfg();

}
