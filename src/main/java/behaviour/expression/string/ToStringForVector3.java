package behaviour.expression.string;

import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionString;
import behaviour.expression.ExpressionVector3;

/**
 * Convert Vector3 to string expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class ToStringForVector3 extends ExpressionString<config.behaviour.expstr.ToStringForVector3> {
    private ExpressionVector3<?> v3Expr = null;

    public ToStringForVector3(config.behaviour.expstr.ToStringForVector3 atomCfg) {
        super(atomCfg);
    }

    public ExpressionVector3<?> getV3Expression() {
        return v3Expr;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void loadFromCfg() {
        v3Expr = (ExpressionVector3) BehaviourFactory.createExpression(getAtomCfg().getV3());
    }
}
