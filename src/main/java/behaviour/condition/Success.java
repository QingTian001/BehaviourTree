package behaviour.condition;

import behaviour.BehaviourTree;

public class Success extends Condition<config.behaviour.condition.Success> {

    public Success(BehaviourTree behaviourTree, config.behaviour.Condition condCfg, Condition<? extends config.behaviour.Condition> parent) {
        super(behaviourTree, condCfg, parent);
    }


    @Override
    public boolean calculateConditionResult() {
        return true;
    }

    @Override
    public boolean calculateConditionResultAndListenEvent() {
        return true;
    }

    @Override
    public void reset(boolean recursive) {
        super.reset(recursive);
    }

    @Override
    public void loadFromCfg() {

    }
}


