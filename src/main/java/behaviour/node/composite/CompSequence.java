package behaviour.node.composite;

import config.behaviour.Statusenum;
import behaviour.BehaviourFactory;
import behaviour.BehaviourHelper;
import behaviour.condition.Condition;

/**
 * CompSequence - Sequential execution composite node (Flyweight).
 * Executes children in order until one fails or all succeed.
 * Logic is in this class, runtime state in CompSequenceTask.
 */
public class CompSequence extends Composite<config.behaviour.node.CompSequence> {

    // Pre-condition configuration (static)
    private Condition<?> preCondConfig;

    public CompSequence(config.behaviour.node.CompSequence atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        super.loadFromCfg();
        // Load pre-condition configuration
        if (!BehaviourHelper.isConditionNull(getAtomCfg().getPrecond())) {
            preCondConfig = BehaviourFactory.createCondition(getAtomCfg().getPrecond());
        }
    }

    @Override
    protected java.util.List<? extends config.behaviour.node.Node> getConfigNodeList() {
        return getAtomCfg().getNodes();
    }

    @Override
    protected java.util.List<? extends config.behaviour.node.Node> getConfigCleanActionList() {
        return getAtomCfg().getCleanActions();
    }

    @Override
    protected java.util.List<? extends config.behaviour.node.Node> getConfigEnterActionList() {
        return getAtomCfg().getEnterActions();
    }

    public Condition<?> getPreCondConfig() {
        return preCondConfig;
    }
}
