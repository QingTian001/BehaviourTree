package behaviour.executor.node.action.actions;
import behaviour.*;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.node.action.actions.ActFindSummonOwner;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.action.actions.ActFindSummonOwnerRuntime;
import config.behaviour.Statusenum;
public final class ActFindSummonOwnerExecutor extends ActionExecutor<ActFindSummonOwner> {
    public ActFindSummonOwnerExecutor(ActFindSummonOwner atom) { super(atom); }
    @Override public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        var context = runtime.getContext();
        long id = ((ActFindSummonOwnerRuntime) runtime).getOwnerInstId();
        GEntity owner = id == 0 ? null : context.getHost().getEntity(id);
        if (owner != null && (owner.isDestroyed() || context.getSelf().getPos().DistSquare(owner.getPos()) >= getAtom().getRadiusSquare())) { owner = null; }
        context.getBlackBoard().putValue(getAtom().getAtomCfg().getWbbKey(), owner);
        return owner == null ? Statusenum.BTFAILURE : Statusenum.BTSUCCESS;
    }
    @Override public void onInterrupt(NodeRuntime runtime) { }
    @Override public void reset(NodeRuntime runtime, boolean recursive) {
        ((ActFindSummonOwnerRuntime) runtime).setOwnerInstId(0);
    }
    @Override public boolean onEnter(NodeRuntime runtime) {
        long id = runtime.getContext().getSelf().getOwnerInstId();
        ((ActFindSummonOwnerRuntime) runtime).setOwnerInstId(id);
        if (id == 0) {
            runtime.getContext().getBlackBoard().putValue(getAtom().getAtomCfg().getWbbKey(), null);
            return false;
        }
        return true;
    }
}
