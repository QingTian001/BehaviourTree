package behaviour.task;

import org.apache.logging.log4j.Level;
import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.BehaviourListenerManager;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.Expression;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.ExpressionRuntime;
import event.IMapObjectEventListener;
import event.MapObjectEvent;
import util.StringUtil;

/**
 * Expression runtime task - only created for preCond usage.
 * Holds runtime state: listener manager, reference to belonging Condition.
 */
public class ExpressionTask<V> extends BehaviourTask<Expression<?, V>> implements ExpressionRuntime<V> {
    private final BehaviourListenerManager listenerManager;
    private final ConditionTask belongCondition;

    public ExpressionTask(Expression<?, V> atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
        this.belongCondition = belongCondition;
        this.listenerManager = belongCondition != null
            ? BehaviourListenerManager.createBehaviourListenerManager(
                    getRuntimeListenerManager(),
                    getBehaviourTreeTask(),
                    belongCondition::setDirty)
            : null;
    }

    /**
     * Calculate expression result without event listening.
     * Delegates to executor and logs result.
     */
    public final V calculate() {
        ExpressionExecutor<?, V> executor = requireExecutor(ExpressionExecutor.class);
        V result = executor.calculate(getContext());
        this.log(Level.DEBUG,
                StringUtil.format("calculate. expr:{}, ret:{}", this.getClass().getSimpleName(), result));
        return result;
    }

    /**
     * Calculate expression result with event listening.
     * Delegates to executor and logs result.
     */
    public final V calculateAndListenEvent() {
        ExpressionExecutor<?, V> executor = requireExecutor(ExpressionExecutor.class);
        V result = executor.calculateAndListenEvent(this);
        this.log(Level.DEBUG,
                StringUtil.format("calculateAndListenEvent. expr:{}, ret:{}", this.getClass().getSimpleName(), result));
        return result;
    }

    public ConditionTask getCondition() {
        return belongCondition;
    }

    @Override
    public ConditionRuntime getConditionRuntime() {
        return belongCondition;
    }

    @Override
    public void setDirty() {
        if (belongCondition != null) {
            belongCondition.setDirty();
        }
    }

    public BehaviourListenerManager getListenerManager() {
        return listenerManager;
    }

    @Override
    public void resetListeners() {
        if (listenerManager != null) {
            listenerManager.onReset();
        }
        super.resetListeners();
    }

    public <Event extends MapObjectEvent> void registerMapObjectEvent(GEntity entity, Class<Event> eventClazz) {
        if (listenerManager != null) {
            listenerManager.registerMapObjectEvent(entity, eventClazz);
        }
    }

    public <Event extends MapObjectEvent> void registerMapObjectEvent(GEntity entity, Class<Event> eventClazz, IMapObjectEventListener<Event> eventListener) {
        if (listenerManager != null) {
            listenerManager.registerMapObjectEvent(entity, eventClazz, eventListener);
        }
    }

    /**
     * Reset ExpressionTask runtime state.
     * Delegates to Expression's reset method for logic.
     * This method is final - subclasses should override Expression's reset method instead.
     *
     * @param recursive ignored for ExpressionTask (Expression has no children in Atom hierarchy)
     */
    @Override
    public final void reset(boolean recursive) {
        ExpressionExecutor<?, V> executor = requireExecutor(ExpressionExecutor.class);
        executor.reset(this);
        disposeRuntimeListeners();
    }
}
