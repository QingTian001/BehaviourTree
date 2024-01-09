package behaviour.node;


import behaviour.condition.Condition;


public interface IPreCondition {
    public Condition<? extends config.behaviour.Condition> getPreCondition();
}
