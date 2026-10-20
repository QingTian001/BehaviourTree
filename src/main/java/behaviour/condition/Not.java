package behaviour.condition;

import behaviour.BehaviourFactory;

/**
 * Not condition - Flyweight pattern.
 * Negates a child condition.
 */
public class Not extends Condition<config.behaviour.condition.Not> {

    private Condition<? extends config.behaviour.condition.Condition> cond = null;

    public Not(config.behaviour.condition.Not atomCfg) {
        super(atomCfg);
    }

    public Condition<? extends config.behaviour.condition.Condition> getCond() {
        return cond;
    }

    @Override
    public void loadFromCfg() {
        cond = BehaviourFactory.createCondition(getAtomCfg().getCond());
    }
}
