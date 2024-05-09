package behaviour.condition;

import config.behaviour.Compareenum;
import behaviour.BehaviourFactory;
import behaviour.BehaviourTree;
import behaviour.expression.ExpressionNumber;

public class Compare extends Condition<config.behaviour.condition.Compare>{

    private ExpressionNumber<? extends config.behaviour.ExpNumber> leftExpr = null;
    private ExpressionNumber<? extends config.behaviour.ExpNumber> rightExpr = null;

    private Compareenum comparator = null;
    public Compare(BehaviourTree behaviourTree, config.behaviour.Condition condCfg, Condition<? extends config.behaviour.Condition> parent) {
        super(behaviourTree, condCfg, parent);
    }

    @Override
    public boolean calculateConditionResult() {
        double leftV = leftExpr.calculateExpression();
        double rightV = rightExpr.calculateExpression();
        return compare(leftV, rightV);
    }

    @Override
    public boolean calculateConditionResultAndListenEvent() {

        double leftV = leftExpr.calculateExpressionAndListenEvent();
        double rightV = rightExpr.calculateExpressionAndListenEvent();
        return compare(leftV, rightV);
    }

    private boolean compare(double leftV, double rightV) {
        if (comparator == Compareenum.EQUAL) {
            return leftV == rightV;
        } else if (comparator == Compareenum.LESS) {
            return leftV < rightV;
        } else if (comparator == Compareenum.GREATER) {
            return leftV > rightV;
        } else if (comparator == Compareenum.LESSEQUAL) {
            return leftV <= rightV;
        } else if (comparator == Compareenum.GREATEREQUAL) {
            return leftV >= rightV;
        } else {
            throw new RuntimeException("unexpected comparator : " + comparator);
        }
    }

    @Override
    public void loadFromCfg() {
        leftExpr = BehaviourFactory.createExpression(this.getBehaviourTree(), getCondCfg().getLeft(), this);
        rightExpr = BehaviourFactory.createExpression(this.getBehaviourTree(), getCondCfg().getRight(), this);

        comparator = getCondCfg().refComarator();
    }

    @Override
    public void reset(boolean recursive) {
        super.reset(recursive);
        leftExpr.reset();
        rightExpr.reset();
    }



}
