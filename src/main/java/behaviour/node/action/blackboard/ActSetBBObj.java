package behaviour.node.action.blackboard;

import config.behaviour.ExpObj;
import config.behaviour.Statusenum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.BehaviourFactory;
import behaviour.BehaviourStack;
import behaviour.BehaviourTree;
import behaviour.expression.ExpressionObject;
import behaviour.node.action.Action;

public class ActSetBBObj extends Action<config.behaviour.node.ActSetBBObj> {
    public static final Logger logger = LogManager.getLogger(ActSetBBObj.class);

    private ExpressionObject<? extends ExpObj, Object> expressionObject = null;
    public ActSetBBObj(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
        super(behaviourTree, nodeCfg);
    }

    @Override
    public void loadFromCfg() {
        expressionObject = BehaviourFactory.createExpression(this.getBehaviourTree(), getNodeCfg().getValue(), null);
    }

    @Override
    protected Statusenum internalUpdate(BehaviourStack stack) {
        Object value = expressionObject.calculateExpression();
        getBehaviourTree().getBlackBoard().putValue(getNodeCfg().getBbKey(), value);
        return Statusenum.BTSUCCESS;
    }

}
