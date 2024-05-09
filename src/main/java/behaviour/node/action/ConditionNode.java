package behaviour.node.action;

import behaviour.node.Node;
import config.behaviour.Statusenum;
import behaviour.BehaviourFactory;
import behaviour.BehaviourStack;
import behaviour.BehaviourTree;
import behaviour.condition.Condition;
import config.behaviour.node.NodeCondition;


public class ConditionNode extends Node<NodeCondition> {

    private Condition<? extends config.behaviour.Condition> cond = null;
    public ConditionNode(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
        super(behaviourTree, nodeCfg);
    }

    @Override
    public void loadFromCfg() {
        cond = BehaviourFactory.createCondition(this.getBehaviourTree(), getNodeCfg().getCond(), null);
    }

    @Override
    protected Statusenum internalUpdate(BehaviourStack stack) {
        boolean ret = cond.calculateConditionResult();
        return ret? Statusenum.BTSUCCESS : Statusenum.BTFAILURE;
    }

    @Override
    public void reset(boolean recursive) {
        super.reset(recursive);
        cond.reset(recursive);
    }
}
