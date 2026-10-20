package behaviour.node.composite;

import behaviour.BehaviourFactory;
import behaviour.BehaviourHelper;
import behaviour.condition.Condition;

/**
 * CompSelector - Selector composite node (Flyweight).
 * Executes children in order until one succeeds or all fail.
 * Logic is in this class, runtime state in CompSelectorTask.
 */
public class CompSelector extends Composite<config.behaviour.node.CompSelector> {

    // Pre-condition configuration (static)
    private Condition<?> preCondConfig;

    public CompSelector(config.behaviour.node.CompSelector atomCfg) {
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
