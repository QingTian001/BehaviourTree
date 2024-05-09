package behaviour.condition;

import config.behaviour.ExpObj;
import config.behaviour.condition.ObjEqual;
import behaviour.BehaviourFactory;
import behaviour.BehaviourTree;
import behaviour.expression.ExpressionObject;

public class ObjectEqual extends Condition<ObjEqual> {

    private ExpressionObject<? extends ExpObj, Object> leftObjExpr = null;
    private ExpressionObject<? extends ExpObj, Object> rightObjExpr = null;

    public ObjectEqual(BehaviourTree behaviourTree, config.behaviour.Condition condCfg, Condition<? extends config.behaviour.Condition> parent) {
        super(behaviourTree, condCfg, parent);
    }

    @Override
    public boolean calculateConditionResult() {
        Object left = leftObjExpr.calculateExpression();
        Object right = rightObjExpr.calculateExpression();
        return left == right;
    }

    @Override
    public boolean calculateConditionResultAndListenEvent() {
        Object left = leftObjExpr.calculateExpressionAndListenEvent();
        Object right = rightObjExpr.calculateExpressionAndListenEvent();
        return left == right;
    }

    @Override
    public void reset(boolean recursive) {
        super.reset(recursive);
        leftObjExpr.reset();
        rightObjExpr.reset();
    }

    @Override
    public void loadFromCfg() {
        ObjEqual objEqualCfg = getCondCfg();

        leftObjExpr = BehaviourFactory.createExpression(this.getBehaviourTree(), objEqualCfg.getLeft(), this);
        rightObjExpr = BehaviourFactory.createExpression(this.getBehaviourTree(), objEqualCfg.getRight(), this);
    }
}
