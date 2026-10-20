package behaviour.executor.condition.conditions;
import behaviour.*;
import behaviour.condition.conditions.IsEntityDestroyed;
import behaviour.executor.condition.ConditionExecutor;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.condition.IsEntityDestroyedRuntime;
import event.object.EventObjectLeaveMap;
public final class IsEntityDestroyedExecutor extends ConditionExecutor<IsEntityDestroyed> {
    public IsEntityDestroyedExecutor(IsEntityDestroyed atom) { super(atom); }
    @Override public boolean calculate(BehaviourContext context) {
        GEntity entity = getAtom().getEntityExpression().calculate(context);
        return entity == null || entity.isDestroyed();
    }
    @Override public boolean calculateAndListenEvent(ConditionRuntime runtime) {
        var task = (IsEntityDestroyedRuntime) runtime;
        GEntity entity = (GEntity) task.getEntityRuntime().calculateAndListenEvent();
        task.setEntity(entity);
        if (entity == null) { return true; }
        if (!task.getListeners().containsKey(entity)) {
            task.getListeners().put(entity, task.listeners().listen(entity, EventObjectLeaveMap.class, event -> task.setDirty()));
        }
        return entity.isDestroyed();
    }
    @Override public void reset(ConditionRuntime runtime, boolean recursive) {
        super.reset(runtime, recursive);
        var task = (IsEntityDestroyedRuntime) runtime;
        if (task.getEntityRuntime() != null) { task.getEntityRuntime().reset(recursive); }
        task.getListeners().clear();
        task.setEntity(null);
    }
}
