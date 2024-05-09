package behaviour.node.action.blackboard;

import config.behaviour.ExpNumber;
import config.behaviour.Statusenum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.BehaviourFactory;
import behaviour.BehaviourStack;
import behaviour.BehaviourTree;
import behaviour.expression.ExpressionNumber;
import behaviour.node.action.Action;

public class ActSetBBNumber extends Action<config.behaviour.node.ActSetBBNumber> {
    public static final Logger logger = LogManager.getLogger(ActSetBBNumber.class);

    private ExpressionNumber<? extends ExpNumber> expressionNumber = null;
    public ActSetBBNumber(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
        super(behaviourTree, nodeCfg);
    }

    @Override
    public void loadFromCfg() {
        expressionNumber = BehaviourFactory.createExpression(this.getBehaviourTree(), getNodeCfg().getValue(), null);
    }

    @Override
    protected Statusenum internalUpdate(BehaviourStack stack) {
        double value = expressionNumber.calculateExpression();
        getBehaviourTree().getBlackBoard().putValue(getNodeCfg().getBbKey(), value);
        return Statusenum.BTSUCCESS;
    }

}
