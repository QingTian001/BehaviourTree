package behaviour.expression.number;
import behaviour.BehaviourFactory;
import behaviour.BehaviourTree;
import behaviour.expression.ExpressionNumber;
import config.behaviour.Calculatorenum;
import config.behaviour.Condition;


/**
 * Created by zyao on 2024/1/2 17:40
 */
public class Calculate extends ExpressionNumber<config.behaviour.expnumber.Calculate> {

    private ExpressionNumber<? extends config.behaviour.ExpNumber> leftExpr = null;
    private ExpressionNumber<? extends config.behaviour.ExpNumber> rightExpr = null;

    @SuppressWarnings("unchecked")
    public Calculate(BehaviourTree behaviourTree, config.behaviour.expnumber.Calculate exprCfg, behaviour.condition.Condition<? extends Condition> condition) {
        super(behaviourTree, exprCfg, condition);
    }

    protected Double internalCalculateExpression() {
        return calculate(false);
    }

    protected Double internalCalculateExpressionAndListenEvent() {
        return calculate(true);
    }

    private double calculate(boolean listenEvent) {
        double leftValue = listenEvent ? leftExpr.calculateExpression() : leftExpr.calculateExpressionAndListenEvent();
        double rightValue = listenEvent ? rightExpr.calculateExpression() : rightExpr.calculateExpressionAndListenEvent();

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
        leftExpr = BehaviourFactory.createExpression(this.getBehaviourTree(), getExprCfg().getLeft(), this.getCondition());
        rightExpr = BehaviourFactory.createExpression(this.getBehaviourTree(), getExprCfg().getRight(), this.getCondition());
    }

    @Override
    public void reset() {
        super.reset();
        leftExpr.reset();
        rightExpr.reset();
    }
}
