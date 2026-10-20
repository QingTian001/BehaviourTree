package behaviour.node.composite;

import behaviour.BehaviourFactory;
import behaviour.BehaviourHelper;
import behaviour.condition.Condition;

/**
 * CompWeightSelector definition.
 */
public class CompWeightSelector extends Composite<config.behaviour.node.CompWeightSelector> {
    private Condition<?> preCondConfig;
    private int[] weights;

    public CompWeightSelector(config.behaviour.node.CompWeightSelector atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        super.loadFromCfg();
        weights = new int[getAtomCfg().getWeightNodes().size()];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = getAtomCfg().getWeightNodes().get(i).getWeight();
        }
        if (!BehaviourHelper.isConditionNull(getAtomCfg().getPrecond())) {
            preCondConfig = BehaviourFactory.createCondition(getAtomCfg().getPrecond());
        }
    }

    @Override
    protected java.util.List<? extends config.behaviour.node.Node> getConfigNodeList() {
        java.util.ArrayList<config.behaviour.node.Node> list = new java.util.ArrayList<>();
        getAtomCfg().getWeightNodes().forEach(weightNode -> list.add(weightNode.getNode()));
        return list;
    }

    @Override
    protected java.util.List<? extends config.behaviour.node.Node> getConfigCleanActionList() {
        return getAtomCfg().getCleanActions();
    }

    @Override
    protected java.util.List<? extends config.behaviour.node.Node> getConfigEnterActionList() {
        return getAtomCfg().getEnterActions();
    }

    public int[] getWeights() {
        return weights;
    }

    public Condition<?> getPreCondConfig() {
        return preCondConfig;
    }
}
