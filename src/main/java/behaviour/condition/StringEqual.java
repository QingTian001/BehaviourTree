package behaviour.condition;

import config.behaviour.condition.StrEqual;
import behaviour.BehaviourFactory;
import behaviour.BehaviourTree;
import behaviour.expression.ExpressionString;

public class StringEqual extends Condition<StrEqual> {

    private ExpressionString<? extends config.behaviour.ExpStr> leftExprStr = null;
    private ExpressionString<? extends config.behaviour.ExpStr> rightExprStr = null;

    public StringEqual(BehaviourTree behaviourTree, config.behaviour.Condition condCfg, Condition<? extends config.behaviour.Condition> parent) {
        super(behaviourTree, condCfg, parent);
    }


    @Override
    public boolean calculateConditionResult() {
        String left = leftExprStr.calculateExpressionString();
        String right = rightExprStr.calculateExpressionString();
        return left.equals(right);
    }

    @Override
    public boolean calculateConditionResultAndListenEvent() {
        String left = leftExprStr.calculateExpressionStringAndListenEvent();
        String right = rightExprStr.calculateExpressionStringAndListenEvent();
        return left.equals(right);
    }

    @Override
    public void reset(boolean recursive) {
        super.reset(recursive);
        leftExprStr.reset();
        rightExprStr.reset();
    }

    @Override
    public void loadFromCfg() {
        StrEqual strEqualCfg = getCondCfg();

        leftExprStr = BehaviourFactory.createExpression(this.getBehaviourTree(), strEqualCfg.getLeft(), this);
        rightExprStr = BehaviourFactory.createExpression(this.getBehaviourTree(), strEqualCfg.getRight(), this);
    }
}
