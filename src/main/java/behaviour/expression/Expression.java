package behaviour.expression;

import behaviour.GEntity;
import behaviour.Atom;
import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.runtime.ExpressionRuntime;
import behaviour.task.BehaviourTask;
import behaviour.task.ConditionTask;
import behaviour.task.ExpressionTask;

/**
 * Expression base class - Flyweight pattern.
 * Two calculation modes:
 * 1. Normal: calculate(BehaviourContext) - no Task created
 * 2. PreCond: calculateAndListenEvent(ExpressionTask) - Task for event listening
 *
 * Delegates calculation to executor and logs the result.
 */
public abstract class Expression<T, V> extends Atom<T> {

    public Expression(T atomCfg) {
        super(atomCfg);
    }

    /**
     * Normal calculation - no runtime object needed.
     * Delegates to executor and logs result.
     */
    public final V calculate(BehaviourContext context) {
        @SuppressWarnings("unchecked")
        V ret = (V) getExecutor(ExpressionExecutor.class).calculate(context);
        context.getBehaviourTreeTask().log(org.apache.logging.log4j.Level.DEBUG,
            this.getClass(),
            getLogHead(context) + "; calculateExpressionResult. expr:{}, ret:{}",
            this.getClass().getSimpleName(), ret);
        return ret;
    }

    /**
     * Calculation with event listening - for preCond usage.
     * Delegates to executor and logs result.
     */
    public final V calculateAndListenEvent(ExpressionTask<V> task) {
        @SuppressWarnings("unchecked")
        V ret = (V) task.requireExecutor(ExpressionExecutor.class).calculateAndListenEvent(task);
        task.getContext().getBehaviourTreeTask().log(org.apache.logging.log4j.Level.DEBUG,
            this.getClass(),
            getLogHead(task.getContext()) + "; calculateExpressionResultAndListenEvent. expr:{}, ret:{}",
            this.getClass().getSimpleName(), ret);
        return ret;
    }

    public <R extends ExpressionRuntime<V>> R createRuntime(ConditionTask parent, BehaviourContext context, Class<R> runtimeClass) {
        return BehaviourFactory.instantiateExpressionRuntime(this, parent, context, runtimeClass);
    }

    /**
     * Get log head prefix for logging.
     * (Same implementation as in Condition.java)
     */
    protected String getLogHead(BehaviourContext context) {
        GEntity self = context.getSelf();
        return String.format("path:%s (#%s), entityInstId:%d, behaviourCfgId:%d",
            getPath(), getPathIdHex(), self == null ? 0 : self.getInstanceId(), context.getBehaviourTreeTask().getId());
    }

    @Override
    public ExpressionTask<V> createRuntime(BehaviourTask<?> parent, BehaviourContext context) {
        return createRuntime((ConditionTask) parent, context, ExpressionTask.class);
    }

}
