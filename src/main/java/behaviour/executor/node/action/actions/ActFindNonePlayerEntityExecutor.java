package behaviour.executor.node.action.actions;
import behaviour.*;
import behaviour.executor.node.action.ActionExecutor;
import behaviour.node.action.actions.ActFindNonePlayerEntity;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.action.actions.ActFindNonePlayerEntityRuntime;
import config.behaviour.Statusenum;
public final class ActFindNonePlayerEntityExecutor extends ActionExecutor<ActFindNonePlayerEntity> {
    public ActFindNonePlayerEntityExecutor(ActFindNonePlayerEntity atom) { super(atom); }
    @Override public boolean onEnter(NodeRuntime runtime) {
        var cfg = getAtom().getAtomCfg();
        runtime.getContext().getBlackBoard().putValue(cfg.getWbbKeyEntity(), null);
        ActFindNonePlayerEntityRuntime action = (ActFindNonePlayerEntityRuntime) runtime;
        long now = runtime.getContext().getHost().getNowMills();
        if (action.getLastEnterTimestamp() != Long.MIN_VALUE && now - action.getLastEnterTimestamp() < ActFindNonePlayerEntity.ENTER_CD) { return false; }
        action.setLastEnterTimestamp(now);
        return true;
    }
    @Override public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        var context = runtime.getContext();
        var cfg = getAtom().getAtomCfg();
        for (GEntity entity : context.getHost().findEntities(context.getSelf(), cfg.getObjType(), cfg.getCfgIdList(), cfg.getRadius())) {
            if (entity == context.getSelf() || entity.isDestroyed() || entity.getPos().DistSquare(context.getSelf().getPos()) > getAtom().getRadiusSquare()) { continue; }
            context.getBlackBoard().putValue(cfg.getWbbKeyEntity(), entity);
            return Statusenum.BTSUCCESS;
        }
        return Statusenum.BTFAILURE;
    }
    @Override public void onInterrupt(NodeRuntime runtime) { }
    @Override public void reset(NodeRuntime runtime, boolean recursive) { }
}
