package behaviour.task.impl.condition.conditions;

import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.TypedNumber;
import behaviour.condition.conditions.CheckIsInInteraction;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.condition.CheckIsInInteractionRuntime;
import behaviour.task.ConditionTask;
import behaviour.task.ExpressionNumberTask;
import behaviour.task.ExpressionObjectTask;

/**
 * CheckIsInInteraction 运行时任务实现
 */
public class CheckIsInInteractionTask extends ConditionTask implements CheckIsInInteractionRuntime {
    private ExpressionObjectTask<?> entityExpressionTask;
    private ExpressionNumberTask abilityIdExpressionTask;
    private ExpressionNumberTask interactionIdExpressionTask;

    public CheckIsInInteractionTask(CheckIsInInteraction atom, ConditionTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public CheckIsInInteraction getAtom() {
        return (CheckIsInInteraction) super.getAtom();
    }

    public ExpressionObjectTask<?> getEntityExpressionTask() {
        return entityExpressionTask;
    }

    public void setEntityExpressionTask(ExpressionObjectTask<?> entityExpressionTask) {
        this.entityExpressionTask = entityExpressionTask;
    }

    @Override
    public ExpressionRuntime<?> getEntityRuntime() {
        return entityExpressionTask;
    }

    public ExpressionNumberTask getAbilityIdExpressionTask() {
        return abilityIdExpressionTask;
    }

    public void setAbilityIdExpressionTask(ExpressionNumberTask abilityIdExpressionTask) {
        this.abilityIdExpressionTask = abilityIdExpressionTask;
    }

    @Override
    public ExpressionRuntime<TypedNumber> getAbilityIdRuntime() {
        return abilityIdExpressionTask;
    }

    public ExpressionNumberTask getInteractionIdExpressionTask() {
        return interactionIdExpressionTask;
    }

    public void setInteractionIdExpressionTask(ExpressionNumberTask interactionIdExpressionTask) {
        this.interactionIdExpressionTask = interactionIdExpressionTask;
    }

    @Override
    public ExpressionRuntime<TypedNumber> getInteractionIdRuntime() {
        return interactionIdExpressionTask;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void init() {
        ExpressionObjectTask<?> entityTask = (ExpressionObjectTask<?>) BehaviourFactory.createExpressionRuntime(
                getAtom().getEntityExpression(), this, getContext());
        ExpressionNumberTask abilityIdTask = (ExpressionNumberTask) BehaviourFactory.createExpressionRuntime(
                getAtom().getAbilityIdExpression(), this, getContext());
        ExpressionNumberTask interactionIdTask = (ExpressionNumberTask) BehaviourFactory.createExpressionRuntime(
                getAtom().getInteractionIdExpression(), this, getContext());
        setEntityExpressionTask(entityTask);
        setAbilityIdExpressionTask(abilityIdTask);
        setInteractionIdExpressionTask(interactionIdTask);
    }
}
