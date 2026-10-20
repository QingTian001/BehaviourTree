package behaviour.condition;

import behaviour.GEntity;
import behaviour.Atom;
import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.executor.condition.ConditionExecutor;
import behaviour.runtime.ConditionRuntime;
import behaviour.task.BehaviourTask;
import behaviour.task.ConditionTask;

/**
 * Condition base class - Flyweight pattern.
 * Two calculation modes:
 * 1. Normal: calculate(BehaviourContext) - no Task created
 * 2. PreCond: calculateAndListenEvent(ConditionTask) - Task for event listening
 *
 * Uses Template Method pattern for logging.
 */
public abstract class Condition<T extends config.behaviour.condition.Condition> extends Atom<T> {

    public Condition(T atomCfg) {
        super(atomCfg);
    }

    /**
     * Normal calculation - no runtime object needed.
     * Delegates to executor and logs result.
     */
    public final boolean calculate(BehaviourContext context) {
        boolean ret = getExecutor(ConditionExecutor.class).calculate(context);
        context.getBehaviourTreeTask().log(org.apache.logging.log4j.Level.DEBUG,
            this.getClass(),
            getLogHead(context) + "; calculateConditionResult. cond:{}, ret:{}",
            this.getClass().getSimpleName(), ret);
        return ret;
    }

    /**
     * Calculation with event listening - for preCond usage.
     * Delegates to executor and logs result.
     */
    public final boolean calculateAndListenEvent(ConditionTask task) {
        boolean ret = task.requireExecutor(ConditionExecutor.class).calculateAndListenEvent(task);
        task.getContext().getBehaviourTreeTask().log(org.apache.logging.log4j.Level.DEBUG,
            this.getClass(),
            getLogHead(task.getContext()) + "; calculateConditionResultAndListenEvent. cond:{}, ret:{}",
            this.getClass().getSimpleName(), ret);
        return ret;
    }

    public <R extends ConditionRuntime> R createRuntime(ConditionTask parent, BehaviourContext context, Class<R> runtimeClass) {
        return BehaviourFactory.instantiateConditionRuntime(this, parent, context, runtimeClass);
    }

    @Override
    public ConditionTask createRuntime(BehaviourTask<?> parent, BehaviourContext context) {
        return createRuntime((ConditionTask) parent, context, ConditionTask.class);
    }

    protected GEntity getSelf(BehaviourContext context) {
        return context.getSelf();
    }

    /**
     * Get log head prefix for logging.
     * Returns a formatted string with entity and map information.
     */
    protected String getLogHead(BehaviourContext context) {
        GEntity self = context.getSelf();
        return String.format("path:%s (#%s), entityInstId:%d, behaviourCfgId:%d",
            getPath(), getPathIdHex(), self == null ? 0 : self.getInstanceId(), context.getBehaviourTreeTask().getId());
    }

}
