package behaviour.node.action.blackboard;

import config.behaviour.expvector3.ExpVector3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionVector3;
import behaviour.node.action.Action;

public class ActSetBBVector3 extends Action<config.behaviour.node.ActSetBBVector3> {
    public static final Logger logger = LogManager.getLogger(ActSetBBVector3.class);

    private ExpressionVector3<? extends ExpVector3> expressionVector3;

    public ActSetBBVector3(config.behaviour.node.ActSetBBVector3 atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        expressionVector3 = BehaviourFactory.createExpression(getAtomCfg().getValue(), null);
    }

    public ExpressionVector3<? extends ExpVector3> getExpressionVector3() {
        return expressionVector3;
    }
}
