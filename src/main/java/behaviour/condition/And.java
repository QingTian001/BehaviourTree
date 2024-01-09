package behaviour.condition;

import behaviour.BehaviourFactory;
import behaviour.BehaviourTree;

public class And extends Condition<config.behaviour.condition.And>{

    private Condition<? extends config.behaviour.Condition> leftCond = null;
    private Condition<? extends config.behaviour.Condition> rightCond = null;
    public And(BehaviourTree behaviourTree, config.behaviour.Condition condCfg, Condition<? extends config.behaviour.Condition> parent) {
        super(behaviourTree, condCfg, parent);
    }

    @Override
    public boolean calculateConditionResult() {
        boolean left = leftCond.calculateConditionResult();
        boolean right = rightCond.calculateConditionResult();

        return left && right;
    }

    @Override
    public boolean calculateConditionResultAndListenEvent() {

        boolean left = leftCond.calculateConditionResultAndListenEvent();
        boolean right = rightCond.calculateConditionResultAndListenEvent();

        return left && right;
    }

    @Override
    public void reset(boolean recursive) {
        super.reset(recursive);
        leftCond.reset(recursive);
        rightCond.reset(recursive);
    }

    @Override
    public void loadFromCfg() {
        leftCond = BehaviourFactory.createCondition(this.getBehaviourTree(), getCondCfg().getCond1(), this);
        rightCond = BehaviourFactory.createCondition(this.getBehaviourTree(), getCondCfg().getCond2(), this);
    }

    @Override
    public void setDirty() {
        super.setDirty();
    }

    @Override
    public void clearDirty() {
        super.clearDirty();
        leftCond.clearDirty();
        rightCond.clearDirty();
    }
}
