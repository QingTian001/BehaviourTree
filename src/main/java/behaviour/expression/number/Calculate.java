package behaviour.expression.number;

import config.behaviour.Calculatorenum;
import behaviour.BehaviourFactory;
import behaviour.BehaviourTree;
import behaviour.condition.Condition;
import behaviour.expression.ExpressionNumber;


public class Calculate extends ExpressionNumber<config.behaviour.expnumber.Calculate> {

    private ExpressionNumber<? extends config.behaviour.ExpNumber> leftExpr = null;
    private ExpressionNumber<? extends config.behaviour.ExpNumber> rightExpr = null;

    @SuppressWarnings("unchecked")
    public Calculate(BehaviourTree behaviourTree, config.behaviour.ExpNumber exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        super(behaviourTree, exprCfg, condition);
    }

    public double calculateExpressionNumber() {
        return calculate();
    }

    public double calculateExpressionNumberAndListenEvent() {
        throw new RuntimeException("not support trigger");
    }

    private double calculate() {
        double leftValue = leftExpr.calculateExpressionNumber();
        double rightValue = rightExpr.calculateExpressionNumber();

        if (getExprCfg().getCalculator() == Calculatorenum.PLUS.getId()) {
            return leftValue + rightValue;
        } else if (getExprCfg().getCalculator() == Calculatorenum.SUB.getId()) {
            return leftValue - rightValue;
        } else if (getExprCfg().getCalculator() == Calculatorenum.MULTI.getId()) {
            return leftValue * rightValue;
        }  else if (getExprCfg().getCalculator() == Calculatorenum.DIVIDE.getId()) {
            return leftValue / rightValue;
        } else {
            throw new RuntimeException("unknown calculator: " + getExprCfg().getCalculator());
        }
    }

    @Override
    public void loadFromCfg() {
        super.loadFromCfg();
        leftExpr = BehaviourFactory.createExpression(this.getBehaviourTree(), getExprCfg().getLeft(), null);
        rightExpr = BehaviourFactory.createExpression(this.getBehaviourTree(), getExprCfg().getRight(), null);
    }

    @Override
    public void reset() {
        super.reset();
        leftExpr.reset();
        rightExpr.reset();
    }
}
