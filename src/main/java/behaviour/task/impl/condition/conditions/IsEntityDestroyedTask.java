package behaviour.task.impl.condition.conditions;

import behaviour.GEntity;
import behaviour.condition.conditions.IsEntityDestroyed;
import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.ListenerHandle;
import behaviour.runtime.condition.IsEntityDestroyedRuntime;
import behaviour.task.ConditionTask;
import behaviour.task.ExpressionObjectTask;

import java.util.HashMap;
import java.util.Map;

/**
 * IsEntityDestroyed 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 鍙繚瀛樿繍琛屾椂鐘舵€侊紝閫昏緫鍦?Atom 绫讳腑
 */
public class IsEntityDestroyedTask extends ConditionTask implements IsEntityDestroyedRuntime {

    private ExpressionObjectTask entityExpressionTask;
    private GEntity entity = null;
    // 鐩戝惉鎵€鏈夌殑Entity绂诲紑浜嬩欢
    private final Map<GEntity, ListenerHandle> listeners = new HashMap<>();

    public IsEntityDestroyedTask(IsEntityDestroyed atom, ConditionTask parentConditionTask, BehaviourContext context) {
        super(atom, parentConditionTask, context);
    }

    @Override
    public IsEntityDestroyed getAtom() {
        return (IsEntityDestroyed) super.getAtom();
    }

    public void setEntityExpressionTask(ExpressionObjectTask entityExpressionTask) {
        this.entityExpressionTask = entityExpressionTask;
    }

    public ExpressionObjectTask getEntityExpressionTask() {
        return entityExpressionTask;
    }

    @Override
    public ExpressionRuntime<?> getEntityRuntime() {
        return entityExpressionTask;
    }

    @Override
    public GEntity getEntity() {
        return entity;
    }

    @Override
    public void setEntity(GEntity entity) {
        this.entity = entity;
    }

    @Override
    public Map<GEntity, ListenerHandle> getListeners() {
        return listeners;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void init() {
        ExpressionObjectTask exprTask = (ExpressionObjectTask) BehaviourFactory.createExpressionRuntime(
                getAtom().getEntityExpression(), this, getContext());
        setEntityExpressionTask(exprTask);
    }
}

