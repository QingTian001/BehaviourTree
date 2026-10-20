package behaviour.executor.condition.conditions;
import behaviour.*;
import behaviour.condition.conditions.CheckIsInInteraction;
import behaviour.executor.condition.ConditionExecutor;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.condition.CheckIsInInteractionRuntime;
import event.object.EventInteractionChanged;
import event.object.EventObjectLeaveMap;
public final class CheckIsInInteractionExecutor extends ConditionExecutor<CheckIsInInteraction> {
    public CheckIsInInteractionExecutor(CheckIsInInteraction atom) { super(atom); }
    @Override public boolean calculate(BehaviourContext context) {
        GEntity target = getAtom().getEntityExpression().calculate(context);
        return target != null && !target.isDestroyed() && context.getHost().isInInteraction(context.getSelf(), target,
                getAtom().getAbilityIdExpression().calculate(context).intValue(), getAtom().getInteractionIdExpression().calculate(context).intValue());
    }
    @Override public boolean calculateAndListenEvent(ConditionRuntime runtime) {
        var task = (CheckIsInInteractionRuntime) runtime;
        GEntity target = (GEntity) task.getEntityRuntime().calculateAndListenEvent();
        int ability = task.getAbilityIdRuntime().calculateAndListenEvent().intValue();
        int interaction = task.getInteractionIdRuntime().calculateAndListenEvent().intValue();
        task.listeners().listen(task.getContext().getSelf(), EventInteractionChanged.class, event -> task.setDirty());
        if (target == null) { return false; }
        task.listeners().listen(target, EventObjectLeaveMap.class, event -> task.setDirty());
        task.listeners().listen(target, EventInteractionChanged.class, event -> task.setDirty());
        return !target.isDestroyed() && task.getContext().getHost().isInInteraction(task.getContext().getSelf(), target, ability, interaction);
    }
    @Override public void reset(ConditionRuntime runtime, boolean recursive) {
        super.reset(runtime, recursive);
        var task = (CheckIsInInteractionRuntime) runtime;
        if (task.getEntityRuntime() != null) { task.getEntityRuntime().reset(recursive); }
        if (task.getAbilityIdRuntime() != null) { task.getAbilityIdRuntime().reset(recursive); }
        if (task.getInteractionIdRuntime() != null) { task.getInteractionIdRuntime().reset(recursive); }
    }
}
