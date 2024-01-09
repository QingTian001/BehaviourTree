package behaviour.node.decorator;

import config.behaviour.Statusenum;
import behaviour.BehaviourFactory;
import behaviour.BehaviourTree;
import behaviour.condition.Condition;


public class LoopUtil extends Decorator<config.behaviour.node.LoopUtil> {
    private Condition<? extends config.behaviour.Condition> cond = null;
    public LoopUtil(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
        super(behaviourTree, nodeCfg);
    }

    @Override
    protected boolean isValidOnFinish() {
        return getNodeCfg().getValidOnFinish();
    }

    @Override
    protected config.behaviour.Node getDecorateNodeCfg() {
        return getNodeCfg().getNode();
    }

    @Override
    public void reset(boolean recursive) {
        super.reset(recursive);
    }

    @Override
    public void loadFromCfg() {
        super.loadFromCfg();
        cond = BehaviourFactory.createCondition(this.getBehaviourTree(), getNodeCfg().getCond(), null);
    }

    @Override
    protected Statusenum decorate(Statusenum childUpdateStatus) {
        if (cond.calculateConditionResult()) {
            return Statusenum.BTSUCCESS;
        }
        return Statusenum.BTRUNNING;
    }
}
