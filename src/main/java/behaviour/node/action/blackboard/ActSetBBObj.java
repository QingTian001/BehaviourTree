package behaviour.node.action.blackboard;

import config.behaviour.expobj.ExpObj;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionObject;
import behaviour.node.action.Action;

public class ActSetBBObj extends Action<config.behaviour.node.ActSetBBObj> {
    public static final Logger logger = LogManager.getLogger(ActSetBBObj.class);

    private ExpressionObject<? extends ExpObj, Object> expressionObject;

    public ActSetBBObj(config.behaviour.node.ActSetBBObj atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        expressionObject = BehaviourFactory.createExpression(getAtomCfg().getValue(), null);
    }

    public ExpressionObject<? extends ExpObj, Object> getExpressionObject() {
        return expressionObject;
    }
}
