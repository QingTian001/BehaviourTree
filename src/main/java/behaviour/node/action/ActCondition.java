package behaviour.node.action;

import behaviour.BehaviourFactory;
import behaviour.condition.Condition;

/**
 * Created by zyao on 2023/12/20 16:03
 */
public class ActCondition extends Action<config.behaviour.node.ActCondition> {

    private Condition<? extends config.behaviour.condition.Condition> cond = null;

    public ActCondition(config.behaviour.node.ActCondition atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        cond = BehaviourFactory.createCondition(getAtomCfg().getCond());
    }

    public Condition<? extends config.behaviour.condition.Condition> getCond() {
        return cond;
    }
}
