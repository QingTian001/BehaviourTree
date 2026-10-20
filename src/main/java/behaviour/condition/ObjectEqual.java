package behaviour.condition;

import config.behaviour.expobj.ExpObj;
import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionObject;

/**
 * ObjectEqual condition - Flyweight pattern.
 * Checks if two object expressions refer to the same object.
 */
public class ObjectEqual extends Condition<config.behaviour.condition.ObjEqual> {

    private ExpressionObject<? extends ExpObj, Object> leftObjExpr = null;
    private ExpressionObject<? extends ExpObj, Object> rightObjExpr = null;

    public ObjectEqual(config.behaviour.condition.ObjEqual atomCfg) {
        super(atomCfg);
    }

    public ExpressionObject<? extends ExpObj, Object> getLeftObjExpr() {
        return leftObjExpr;
    }

    public ExpressionObject<? extends ExpObj, Object> getRightObjExpr() {
        return rightObjExpr;
    }

    @Override
    public void loadFromCfg() {
        leftObjExpr = BehaviourFactory.createExpression(getAtomCfg().getLeft());
        rightObjExpr = BehaviourFactory.createExpression(getAtomCfg().getRight());
    }
}
