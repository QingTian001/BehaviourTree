package behaviour.condition;

import behaviour.BehaviourFactory;

/**
 * Or condition - Flyweight pattern.
 * Computes logical OR of two child conditions.
 */
public class Or extends Condition<config.behaviour.condition.Or> {

    private Condition<? extends config.behaviour.condition.Condition> leftCond = null;
    private Condition<? extends config.behaviour.condition.Condition> rightCond = null;

    public Or(config.behaviour.condition.Or atomCfg) {
        super(atomCfg);
    }

    public Condition<? extends config.behaviour.condition.Condition> getLeftCond() {
        return leftCond;
    }

    public Condition<? extends config.behaviour.condition.Condition> getRightCond() {
        return rightCond;
    }

    @Override
    public void loadFromCfg() {
        leftCond = BehaviourFactory.createCondition(getAtomCfg().getCond1());
        rightCond = BehaviourFactory.createCondition(getAtomCfg().getCond2());
    }
}
