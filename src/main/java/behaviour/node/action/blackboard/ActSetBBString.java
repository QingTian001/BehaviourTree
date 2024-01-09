package behaviour.node.action.blackboard;

import config.behaviour.ExpStr;
import config.behaviour.Statusenum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.BehaviourFactory;
import behaviour.BehaviourStack;
import behaviour.BehaviourTree;
import behaviour.expression.ExpressionString;
import behaviour.node.action.Action;

public class ActSetBBString extends Action<config.behaviour.node.ActSetBBStr> {
    public static final Logger logger = LogManager.getLogger(ActSetBBString.class);

    private ExpressionString<? extends ExpStr> expressionStr = null;
    public ActSetBBString(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
        super(behaviourTree, nodeCfg);
    }

    @Override
    public void loadFromCfg() {
        expressionStr = BehaviourFactory.createExpression(this.getBehaviourTree(), getNodeCfg().getValue(), null);
    }

    @Override
    protected Statusenum internalUpdate(BehaviourStack stack) {
        String value = expressionStr.calculateExpressionString();
        getBehaviourTree().getBlackBoard().putValue(getNodeCfg().getBbKey(), value);
        return Statusenum.BTSUCCESS;
    }

}
