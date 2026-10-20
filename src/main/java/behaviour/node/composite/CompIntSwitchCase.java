package behaviour.node.composite;

import behaviour.BehaviourFactory;
import behaviour.BehaviourHelper;
import behaviour.condition.Condition;
import behaviour.expression.ExpressionNumber;
import behaviour.node.Node;

/**
 * CompIntSwitchCase definition.
 */
public class CompIntSwitchCase extends Composite<config.behaviour.node.CompIntSwitchCase> {
    private Condition<?> preCondConfig;
    private ExpressionNumber<?> caseValueExpression;
    private Node<?> defaultCase;
    private int[] cases;

    public CompIntSwitchCase(config.behaviour.node.CompIntSwitchCase atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        super.loadFromCfg();
        cases = new int[getAtomCfg().getCases().size()];
        for (int i = 0; i < cases.length; i++) {
            cases[i] = getAtomCfg().getCases().get(i).getCaseInt();
        }
        if (!BehaviourHelper.isConditionNull(getAtomCfg().getPrecond())) {
            preCondConfig = BehaviourFactory.createCondition(getAtomCfg().getPrecond());
        }
        @SuppressWarnings({"unchecked", "rawtypes"})
        var expr = (ExpressionNumber) BehaviourFactory.createExpression(getAtomCfg().getCaseValue());
        caseValueExpression = expr;
        defaultCase = BehaviourFactory.createNode(getAtomCfg().getDefaultCase());
    }

    @Override
    protected java.util.List<? extends config.behaviour.node.Node> getConfigNodeList() {
        java.util.ArrayList<config.behaviour.node.Node> list = new java.util.ArrayList<>();
        getAtomCfg().getCases().forEach(caseUnit -> list.add(caseUnit.getNode()));
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

    public ExpressionNumber<?> getCaseValueExpression() {
        return caseValueExpression;
    }

    public Node<?> getDefaultCase() {
        return defaultCase;
    }

    public int[] getCases() {
        return cases;
    }

    public Condition<?> getPreCondConfig() {
        return preCondConfig;
    }
}
