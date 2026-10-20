package behaviour.node.decorator;

import behaviour.BehaviourFactory;
import behaviour.BehaviourHelper;
import behaviour.condition.Condition;

/**
 * 前置条件装饰节点定义。
 */
public class DecCondition extends Decorator<config.behaviour.node.DecCondition> {
    private Condition<?> condition;

    public DecCondition(config.behaviour.node.DecCondition atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        if (BehaviourHelper.isConditionNull(getAtomCfg().getCond())) {
            throw new IllegalArgumentException("DecCondition cond is null");
        }
        super.loadFromCfg();
        condition = BehaviourFactory.createCondition(getAtomCfg().getCond());
    }

    public Condition<?> getCondition() {
        return condition;
    }

    @Override
    protected config.behaviour.node.Node getDecorateNodeCfg() {
        return getAtomCfg().getNode();
    }
}
