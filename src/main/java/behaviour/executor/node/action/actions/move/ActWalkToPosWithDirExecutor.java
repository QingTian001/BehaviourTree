package behaviour.executor.node.action.actions.move;
import behaviour.BehaviourOperation;
import behaviour.BehaviourStack;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.node.action.actions.move.ActWalkToPosWithDir;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.action.actions.move.ActWalkToPosWithDirRuntime;
import config.behaviour.Statusenum;
public final class ActWalkToPosWithDirExecutor extends ActionExecutor<ActWalkToPosWithDir> {
    public ActWalkToPosWithDirExecutor(ActWalkToPosWithDir atom) { super(atom); }
    @Override public boolean onEnter(NodeRuntime runtime) {
        ActWalkToPosWithDirRuntime action = (ActWalkToPosWithDirRuntime) runtime;
        math.Vector3 target = getAtom().getPosExpression().calculate(runtime.getContext());
        if (target == null || runtime.getContext().getSelf().isDestroyed()) { return false; }
        BehaviourOperation operation = runtime.getContext().getHost().moveTo(runtime.getContext().getSelf(), target, getAtom().getDirExpression().calculate(runtime.getContext()).floatValue());
        action.setMoveToPos(operation);
        return operation != null;
    }
    @Override public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        ActWalkToPosWithDirRuntime action = (ActWalkToPosWithDirRuntime) runtime;
        if (runtime.getContext().getSelf().isDestroyed()) { onInterrupt(runtime); return Statusenum.BTFAILURE; }
        BehaviourOperation operation = action.getMoveToPos();
        if (operation == null) { return Statusenum.BTFAILURE; }
        Statusenum status = operation.update();
        if (status == Statusenum.BTSUCCESS || status == Statusenum.BTFAILURE) { action.setMoveToPos(null); }
        else if (status != Statusenum.BTRUNNING) { throw new IllegalStateException("宿主操作返回非法状态: " + status); }
        return status;
    }
    @Override public void onInterrupt(NodeRuntime runtime) {
        ActWalkToPosWithDirRuntime action = (ActWalkToPosWithDirRuntime) runtime;
        BehaviourOperation operation = action.getMoveToPos();
        action.setMoveToPos(null);
        if (operation != null) { operation.cancel(); }
    }
    @Override public void reset(NodeRuntime runtime, boolean recursive) {
        onInterrupt(runtime);
        
    }
}
