package behaviour.condition;

import behaviour.BehaviourFactory;

/**
 * And condition - Flyweight pattern.
 * Computes logical AND of two child conditions.
 */
public class And extends Condition<config.behaviour.condition.And> {

    private Condition<? extends config.behaviour.condition.Condition> leftCond = null;
    private Condition<? extends config.behaviour.condition.Condition> rightCond = null;

    public And(config.behaviour.condition.And atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        leftCond = BehaviourFactory.createCondition(getAtomCfg().getCond1());
        rightCond = BehaviourFactory.createCondition(getAtomCfg().getCond2());
    }

    public Condition<? extends config.behaviour.condition.Condition> getLeftCond() {
        return leftCond;
    }

    public Condition<? extends config.behaviour.condition.Condition> getRightCond() {
        return rightCond;
    }
}
