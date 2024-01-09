package behaviour.node.action;

import behaviour.BehaviourTree;
import behaviour.node.Node;

public abstract class Action<T extends config.behaviour.Node> extends Node<T> {
    public Action(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
        super(behaviourTree, nodeCfg);
    }

}
