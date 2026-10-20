package behaviour.node.action.blackboard;

import config.behaviour.expnumber.ExpNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionNumber;
import behaviour.node.action.Action;

public class ActSetBBNumber extends Action<config.behaviour.node.ActSetBBNumber> {
    public static final Logger logger = LogManager.getLogger(ActSetBBNumber.class);

    private ExpressionNumber<? extends ExpNumber> expressionNumber;

    public ActSetBBNumber(config.behaviour.node.ActSetBBNumber atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        expressionNumber = BehaviourFactory.createExpression(getAtomCfg().getValue());
    }

    public ExpressionNumber<? extends ExpNumber> getExpressionNumber() {
        return expressionNumber;
    }
}
