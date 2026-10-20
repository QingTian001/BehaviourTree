package behaviour.expression.object;

import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionNumber;

/**
 * Get entity by instance ID expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class GetEntityByInstId extends ExpressionGEntity<config.behaviour.expobj.GetEntityByInstId> {
    private ExpressionNumber<?> instIdExpr;

    public GetEntityByInstId(config.behaviour.expobj.GetEntityByInstId atomCfg) {
        super(atomCfg);
    }

    public ExpressionNumber<?> getInstIdExpr() {
        return instIdExpr;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void loadFromCfg() {
        instIdExpr = (ExpressionNumber) BehaviourFactory.createExpression(getAtomCfg().getInstId());
    }
}
