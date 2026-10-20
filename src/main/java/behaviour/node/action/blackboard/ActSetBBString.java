package behaviour.node.action.blackboard;

import config.behaviour.expstr.ExpStr;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionString;
import behaviour.node.action.Action;

public class ActSetBBString extends Action<config.behaviour.node.ActSetBBStr> {
    public static final Logger logger = LogManager.getLogger(ActSetBBString.class);

    private ExpressionString<? extends ExpStr> expressionStr;

    public ActSetBBString(config.behaviour.node.ActSetBBStr atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        expressionStr = BehaviourFactory.createExpression(getAtomCfg().getValue(), null);
    }

    public ExpressionString<? extends ExpStr> getExpressionStr() {
        return expressionStr;
    }
}
