package behaviour.task;

import org.apache.logging.log4j.Level;
import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.BehaviourListenerManager;
import behaviour.condition.Condition;
import behaviour.executor.condition.ConditionExecutor;
import behaviour.runtime.ConditionRuntime;
import event.IMapObjectEventListener;
import event.MapObjectEvent;
import util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Condition runtime task - only created for preCond usage.
 * Holds runtime state: dirty flag, listener manager, and custom state for specific conditions.
 */
public class ConditionTask extends BehaviourTask<Condition<?>> implements ConditionRuntime {
    private boolean dirty = false;
    private final ConditionTask parentCondition;
    private final BehaviourListenerManager listenerManager;
    // Custom state storage for specific conditions (e.g., IsEntityMoved's isMoved flag)
    private final Map<String, Object> state = new HashMap<>();

    public ConditionTask(Condition<?> atom, ConditionTask parentCondition, BehaviourContext context) {
        super(atom, parentCondition, context);
        this.parentCondition = parentCondition;
        this.listenerManager = BehaviourListenerManager.createBehaviourListenerManager(this);
    }

    /**
     * Calculate condition result without event listening.
     * Delegates to executor and logs result.
     */
    public final boolean calculate() {
        ConditionExecutor<?> executor = requireExecutor(ConditionExecutor.class);
        boolean result = executor.calculate(getContext());
        this.log(Level.DEBUG,
                StringUtil.format("calculate. cond:{}, ret:{}", this.getClass().getSimpleName(), result));
        return result;
    }

    /**
     * Calculate condition result with event listening.
     * Delegates to executor and logs result.
     */
    public final boolean calculateAndListenEvent() {
        ConditionExecutor<?> executor = requireExecutor(ConditionExecutor.class);
        boolean result = executor.calculateAndListenEvent(this);
        this.log(Level.DEBUG,
                StringUtil.format("calculateAndListenEvent. cond:{}, ret:{}", this.getClass().getSimpleName(), result));
        return result;
    }

    /**
     * Store custom state (for conditions like IsEntityMoved to track isMoved flag)
     */
    public void setState(String key, Object value) {
        state.put(key, value);
    }

    /**
     * Retrieve custom state
     */
    @SuppressWarnings("unchecked")
    public <T> T getState(String key, T defaultValue) {
        return (T) state.getOrDefault(key, defaultValue);
    }

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

    public ConditionTask getParentCondition() {
        return parentCondition;
    }

    @Override
    public ConditionRuntime getParentConditionRuntime() {
        return parentCondition;
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
        listenerManager.registerMapObjectEvent(entity, eventClazz);
    }

    public <Event extends MapObjectEvent> void registerMapObjectEvent(GEntity entity, Class<Event> eventClazz, IMapObjectEventListener<Event> eventListener) {
        listenerManager.registerMapObjectEvent(entity, eventClazz, eventListener);
    }

    /**
     * Calculate condition result - alias for calculate()
     */
    public final boolean calculateConditionResult() {
        // 依赖可能因黑板目标或短路分支改变，先释放旧订阅，再通过原 Task 重建。
        reset(false);
        return calculateAndListenEvent();
    }

    /**
     * Reset ConditionTask runtime state.
     * Delegates to Condition's reset method for logic.
     * This method is final - subclasses should override Condition's reset method instead.
     *
     * @param recursive if true, reset child conditions/expressions recursively
     */
    @Override
    public final void reset(boolean recursive) {
        ConditionExecutor<?> executor = requireExecutor(ConditionExecutor.class);
        executor.reset(this, recursive);
        // Reset local state
        this.dirty = false;
        this.state.clear();
        disposeRuntimeListeners();
    }

}
