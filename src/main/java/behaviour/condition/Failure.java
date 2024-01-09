package behaviour.condition;

import behaviour.BehaviourTree;

public class Failure extends Condition<config.behaviour.condition.Failure> {

    public Failure(BehaviourTree behaviourTree, config.behaviour.Condition condCfg, Condition<? extends config.behaviour.Condition> parent) {
        super(behaviourTree, condCfg, parent);
    }


    @Override
    public boolean calculateConditionResult() {
        return false;
    }

    @Override
    public boolean calculateConditionResultAndListenEvent() {
        return false;
    }

    @Override
    public void reset(boolean recursive) {
        super.reset(recursive);
    }

    @Override
    public void loadFromCfg() {

    }
}


