package behaviour.node;

import config.behaviour.Behaviour;
import behaviour.BehaviourFactory;
import java.util.Objects;

/**
 * Created by zyao on 2023/12/20 16:37
 *
 * Refactored to Flyweight pattern:
 * - SubTree (Node) holds static config only
 * - SubTreeTask holds runtime state (subTreeTask reference)
 */
public class SubTree extends Node<config.behaviour.node.SubTree> {
    private Behaviour subBehaviourCfg;

    public SubTree(config.behaviour.node.SubTree nodeCfg) {
        super(nodeCfg);
    }

    @Override
    public void loadFromCfg() {
        subBehaviourCfg = getAtomCfg().refSubTreeId();
    }

    public Behaviour getSubBehaviourCfg() {
        return subBehaviourCfg;
    }
}
