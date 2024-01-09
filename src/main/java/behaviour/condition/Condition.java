package behaviour.condition;

import behaviour.BehaviourTree;

public abstract class Condition<T extends config.behaviour.Condition> {

    private T condCfg;

    private boolean dirty = false;

    // 可能为null
    private Condition<? extends config.behaviour.Condition> parentCondition;
    private final BehaviourTree behaviourTree;
    @SuppressWarnings("unchecked")
    public Condition(BehaviourTree behaviourTree, config.behaviour.Condition condCfg, Condition<? extends config.behaviour.Condition> parentCondition) {
        this.condCfg = (T)condCfg;
        this.parentCondition = parentCondition;
        this.behaviourTree = behaviourTree;
    }

    public T getCondCfg() {
        return condCfg;
    }

    public BehaviourTree getBehaviourTree() {
        return behaviourTree;
    }

    public abstract boolean calculateConditionResult();

    public abstract boolean calculateConditionResultAndListenEvent();

    public void setDirty() {
        dirty = true;
        if (parentCondition != null) {
            parentCondition.setDirty();
        }
    }

    public void clearDirty() {
        this.dirty = false;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void reset(boolean recursive) {
        this.dirty = false;
    }

    public abstract void loadFromCfg();
}
