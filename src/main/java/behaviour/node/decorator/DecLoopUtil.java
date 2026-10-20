package behaviour.node.decorator;

import behaviour.BehaviourFactory;
import behaviour.condition.Condition;

public class DecLoopUtil extends StatusDecorator<config.behaviour.node.DecLoopUtil> {
    private Condition<? extends config.behaviour.condition.Condition> cond;

    public DecLoopUtil(config.behaviour.node.DecLoopUtil atomCfg) {
        super(atomCfg);
    }

    @Override
    public boolean isApplyOnFinishOnly() {
        return getAtomCfg().getValidOnFinish();
    }

    @Override
    protected config.behaviour.node.Node getDecorateNodeCfg() {
        return getAtomCfg().getNode();
    }

    @Override
    public void loadFromCfg() {
        super.loadFromCfg();
        cond = BehaviourFactory.createCondition(getAtomCfg().getCond());
    }

    public Condition<? extends config.behaviour.condition.Condition> getCond() {
        return cond;
    }
}
