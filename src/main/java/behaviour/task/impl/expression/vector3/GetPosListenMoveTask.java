package behaviour.task.impl.expression.vector3;

import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.expression.vector3.GetPosListenMove;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.ListenerHandle;
import behaviour.runtime.expression.vector3.GetPosListenMoveRuntime;
import behaviour.task.ConditionTask;
import behaviour.task.ExpressionObjectTask;
import behaviour.task.ExpressionVector3Task;
import math.Vector3;

/**
 * GetPosListenMove 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 鐢?migrate_expressions.py 鑷姩鐢熸垚
 */
public class GetPosListenMoveTask extends ExpressionVector3Task implements GetPosListenMoveRuntime {

    // 瀛愯〃杈惧紡浠诲姟
    private ExpressionObjectTask<GEntity> entityTask;
    private ListenerHandle moveListener;
    private boolean listening;
    private GEntity listeningEntity;

    public GetPosListenMoveTask(GetPosListenMove atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public GetPosListenMove getAtom() {
        return (GetPosListenMove) super.getAtom();
    }

    @Override
    public void init() {
        @SuppressWarnings({"unchecked", "rawtypes"})
        ExpressionObjectTask<GEntity> childTask = (ExpressionObjectTask<GEntity>) BehaviourFactory.createExpressionRuntime(
                getAtom().getEntityExpression(), getCondition(), getContext());
        entityTask = childTask;
    }

    public ExpressionObjectTask<GEntity> getEntityExpressionTask() {
        return entityTask;
    }

    public void setEntityExpressionTask(ExpressionObjectTask<GEntity> entityTask) {
        this.entityTask = entityTask;
    }

    @Override
    public ExpressionRuntime<GEntity> getEntityRuntime() {
        return entityTask;
    }

    @Override
    public ListenerHandle getMoveListener() {
        return moveListener;
    }

    @Override
    public void setMoveListener(ListenerHandle handle) {
        this.moveListener = handle;
    }

    @Override
    public boolean isListening() {
        return listening;
    }

    @Override
    public void setListening(boolean value) {
        this.listening = value;
    }

    @Override
    public GEntity getListeningEntity() {
        return listeningEntity;
    }

    @Override
    public void setListeningEntity(GEntity entity) {
        this.listeningEntity = entity;
    }

}

