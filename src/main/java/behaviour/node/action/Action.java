package behaviour.node.action;

import behaviour.node.Node;

/**
 * Action base class - Flyweight pattern.
 * Static configuration only. Runtime state in ActionTask.
 */
public abstract class Action<T extends config.behaviour.node.Node> extends Node<T> {

    public Action(T atomCfg) {
        super(atomCfg);
    }
}
