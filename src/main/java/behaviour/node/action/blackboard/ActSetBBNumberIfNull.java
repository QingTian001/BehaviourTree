package behaviour.node.action.blackboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionNumber;
import behaviour.node.action.Action;

public class ActSetBBNumberIfNull extends Action<config.behaviour.node.ActSetBBNumberIfNull> {
    public static final Logger logger = LogManager.getLogger(ActSetBBNumberIfNull.class);
    private ExpressionNumber<?> expressionNumber;

    public ActSetBBNumberIfNull(config.behaviour.node.ActSetBBNumberIfNull atomCfg) {
        super(atomCfg);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void loadFromCfg() {
        expressionNumber = (ExpressionNumber) BehaviourFactory.createExpression(getAtomCfg().getValue());
    }

    public ExpressionNumber<?> getExpressionNumber() {
        return expressionNumber;
    }
}
