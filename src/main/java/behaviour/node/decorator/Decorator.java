package behaviour.node.decorator;

import config.behaviour.Statusenum;
import behaviour.BehaviourFactory;
import behaviour.BehaviourStack;
import behaviour.BehaviourTree;
import behaviour.node.Node;


public abstract class Decorator<T extends config.behaviour.Node> extends Node<T> {

    private Node<? extends config.behaviour.Node> node = null;

    public Decorator(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
        super(behaviourTree, nodeCfg);
    }

    @Override
    public void loadFromCfg() {
        config.behaviour.Node decorateNodeCfg = getDecorateNodeCfg();
        node = BehaviourFactory.createNode(getBehaviourTree(), decorateNodeCfg);
        node.setParent(this);
    }

    protected abstract boolean isValidOnFinish();

    protected abstract config.behaviour.Node getDecorateNodeCfg();

    protected abstract Statusenum decorate(Statusenum childUpdateStatus);

    @Override
    protected Statusenum internalUpdate(BehaviourStack stack) {
        Statusenum status = node.update(stack);
        if (!isValidOnFinish() || status != Statusenum.BTRUNNING) {
            return decorate(status);
        }
        return Statusenum.BTRUNNING;
    }

    protected Statusenum internalUpdate(BehaviourStack stack, Statusenum childStatus) {
        return decorate(childStatus);
    }

    @Override
    public void reset(boolean recursive) {
        super.reset(recursive);
        node.reset(recursive);
    }

    @Override
    public void genNodeId() {
        super.genNodeId();
        node.genNodeId();
    }
}
