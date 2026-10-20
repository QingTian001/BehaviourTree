package behaviour.executor.node.action.actions.move;

import config.behaviour.Statusenum;
import behaviour.GEntity;
import behaviour.BehaviourStack;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.node.action.actions.move.ActFaceToEntity;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.action.actions.move.ActFaceToEntityRuntime;
import math.Vector3;

public final class ActFaceToEntityExecutor extends ActionExecutor<ActFaceToEntity> {
    public ActFaceToEntityExecutor(ActFaceToEntity atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        ActFaceToEntityRuntime actionRuntime = (ActFaceToEntityRuntime) runtime;
        GEntity target = getAtom().getTargetExpr().calculate(runtime.getContext());
        if (target == null || target.isDestroyed() || target == runtime.getContext().getSelf()) {
            return false;
        }
        GEntity self = runtime.getContext().getSelf();
        math.Vector3 from = self.getPos(), to = target.getPos();
        float direction = (float) Math.toDegrees(Math.atan2(to.x - from.x, to.z - from.z));
        runtime.getContext().getHost().changeDirection(self, direction);


        actionRuntime.setTarget(target);
        actionRuntime.setFinishTimeMs(runtime.getContext().getHost().getNowMills() + ActFaceToEntity.DURATION_MS);
        return true;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        ActFaceToEntityRuntime actionRuntime = (ActFaceToEntityRuntime) runtime;
        return runtime.getContext().getHost().getNowMills() >= actionRuntime.getFinishTimeMs()
                ? Statusenum.BTSUCCESS
                : Statusenum.BTRUNNING;
    }

    @Override
    public void onInterrupt(NodeRuntime runtime) {
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
        ActFaceToEntityRuntime actionRuntime = (ActFaceToEntityRuntime) runtime;
        actionRuntime.setTarget(null);
        actionRuntime.setFinishTimeMs(0);
    }
}
