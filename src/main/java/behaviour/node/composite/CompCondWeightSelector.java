package behaviour.node.composite;

import behaviour.BehaviourFactory;
import behaviour.BehaviourHelper;
import behaviour.condition.Condition;

/**
 * CompCondWeightSelector definition.
 */
public class CompCondWeightSelector extends Composite<config.behaviour.node.CompCondWeightSelector> {
    private Condition<?> preCondConfig;
    private int[] weights;
    private Condition<?>[] weightConds;

    public CompCondWeightSelector(config.behaviour.node.CompCondWeightSelector atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        super.loadFromCfg();
        int size = getAtomCfg().getWeightNodes().size();
        weights = new int[size];
        weightConds = new Condition[size];
        for (int i = 0; i < size; i++) {
            weights[i] = getAtomCfg().getWeightNodes().get(i).getWeight();
            weightConds[i] = BehaviourFactory.createCondition(getAtomCfg().getWeightNodes().get(i).getCond());
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

    public Condition<?>[] getWeightConds() {
        return weightConds;
    }

    public Condition<?> getPreCondConfig() {
        return preCondConfig;
    }
}
