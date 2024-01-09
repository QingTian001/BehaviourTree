package behaviour.condition;

import behaviour.BehaviourFactory;
import behaviour.BehaviourTree;

public class Not extends Condition<config.behaviour.condition.Not>{

    private Condition<? extends config.behaviour.Condition> cond = null;
    public Not(BehaviourTree behaviourTree, config.behaviour.Condition condCfg, Condition<? extends config.behaviour.Condition> parent) {
        super(behaviourTree, condCfg, parent);
    }


    @Override
    public boolean calculateConditionResult() {
        return !cond.calculateConditionResult();
    }

    @Override
    public boolean calculateConditionResultAndListenEvent() {
        return !cond.calculateConditionResultAndListenEvent();
    }

    @Override
    public void reset(boolean recursive) {
        super.reset(recursive);
        cond.reset(recursive);
    }

    @Override
    public void loadFromCfg() {
        cond = BehaviourFactory.createCondition(this.getBehaviourTree(), getCondCfg().getCond(), this);
    }

    @Override
    public void clearDirty() {
        super.clearDirty();
        cond.clearDirty();

    }
}
