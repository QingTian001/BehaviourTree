package behaviour.executor.node.action;
import behaviour.BehaviourStack;
import behaviour.node.action.ActDebugMsg;
import behaviour.runtime.NodeRuntime;
import config.behaviour.Statusenum;
import org.apache.logging.log4j.LogManager;
/** 独立工程仅输出本地日志。 */
public final class ActDebugMsgExecutor extends ActionExecutor<ActDebugMsg> {
    public ActDebugMsgExecutor(ActDebugMsg atom) { super(atom); }
    @Override public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        Object[] params = getAtom().getParamsList().stream().map(expr -> expr.calculate(runtime.getContext())).toArray();
        LogManager.getLogger(ActDebugMsg.class).debug(getAtom().getMsgExpression().calculate(runtime.getContext()), params);
        return Statusenum.BTSUCCESS;
    }
    @Override public void onInterrupt(NodeRuntime runtime) { }
    @Override public void reset(NodeRuntime runtime, boolean recursive) { }
    @Override public boolean onEnter(NodeRuntime runtime) { return true; }
}
