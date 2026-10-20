package behaviour.executor.node.action.actions.interaction;
import behaviour.BehaviourOperation;
import behaviour.BehaviourStack;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.node.action.actions.interaction.ActInteractionExit;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.action.actions.interaction.ActInteractionExitRuntime;
import config.behaviour.Statusenum;
public final class ActInteractionExitExecutor extends ActionExecutor<ActInteractionExit> {
    public ActInteractionExitExecutor(ActInteractionExit atom) { super(atom); }
    @Override public boolean onEnter(NodeRuntime runtime) {
        ActInteractionExitRuntime action = (ActInteractionExitRuntime) runtime;
        behaviour.GEntity target = getAtom().getEntityExpression().calculate(runtime.getContext());
        if (target == null || target.isDestroyed() || runtime.getContext().getSelf().isDestroyed()) { return false; }
        action.setTarget(target);
        int ability = getAtom().getAbilityIdExpression().calculate(runtime.getContext()).intValue();
        int interaction = getAtom().getInteractionIdExpression().calculate(runtime.getContext()).intValue();
        long data = getAtom().getDataInteractionIdExpression().calculate(runtime.getContext()).longValue();
        BehaviourOperation operation = runtime.getContext().getHost().interact(runtime.getContext().getSelf(), target, ability, interaction, data, false);
        action.setBehaviourOperation(operation);
        return operation != null;
    }
    @Override public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        ActInteractionExitRuntime action = (ActInteractionExitRuntime) runtime;
        if (runtime.getContext().getSelf().isDestroyed() || action.getTarget().isDestroyed()) { onInterrupt(runtime); return Statusenum.BTFAILURE; }
        BehaviourOperation operation = action.getBehaviourOperation();
        if (operation == null) { return Statusenum.BTFAILURE; }
        Statusenum status = operation.update();
        if (status == Statusenum.BTSUCCESS || status == Statusenum.BTFAILURE) { action.setBehaviourOperation(null); }
        else if (status != Statusenum.BTRUNNING) { throw new IllegalStateException("宿主操作返回非法状态: " + status); }
        return status;
    }
    @Override public void onInterrupt(NodeRuntime runtime) {
        ActInteractionExitRuntime action = (ActInteractionExitRuntime) runtime;
        BehaviourOperation operation = action.getBehaviourOperation();
        action.setBehaviourOperation(null);
        if (operation != null) { operation.cancel(); }
    }
    @Override public void reset(NodeRuntime runtime, boolean recursive) {
        onInterrupt(runtime);
        ((ActInteractionExitRuntime) runtime).setTarget(null);
    }
}
