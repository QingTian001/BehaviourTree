package behaviour.node.composite;

import behaviour.BehaviourFactory;
import behaviour.BehaviourHelper;
import behaviour.condition.Condition;
import behaviour.expression.ExpressionNumber;

/**
 * CompParamWeightSelector definition.
 */
public class CompParamWeightSelector extends Composite<config.behaviour.node.CompParamWeightSelector> {
    private Condition<?> preCondConfig;
    private ExpressionNumber<?>[] weightExps;

    public CompParamWeightSelector(config.behaviour.node.CompParamWeightSelector atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        super.loadFromCfg();
        weightExps = new ExpressionNumber<?>[getAtomCfg().getWeightNodes().size()];
        for (int i = 0; i < weightExps.length; i++) {
            @SuppressWarnings({"unchecked", "rawtypes"})
            var expr = (ExpressionNumber) BehaviourFactory.createExpression(getAtomCfg().getWeightNodes().get(i).getWeight());
            weightExps[i] = expr;
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

    public ExpressionNumber<?>[] getWeightExps() {
        return weightExps;
    }

    public Condition<?> getPreCondConfig() {
        return preCondConfig;
    }
}
