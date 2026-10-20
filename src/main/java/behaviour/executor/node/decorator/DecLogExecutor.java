package behaviour.executor.node.decorator;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import behaviour.node.decorator.DecLog;
import behaviour.runtime.node.decorator.StatusDecoratorRuntime;

public final class DecLogExecutor extends StatusDecoratorExecutor<DecLog> {
    public DecLogExecutor(DecLog atom) {
        super(atom);
    }

    @Override
    protected Statusenum applyDecoration(StatusDecoratorRuntime runtime, Statusenum childStatus) {
        var btTask = runtime.getContext().getBehaviourTreeTask();
        btTask.log(Level.INFO,
                getAtom().getClass(),
                "BehaviourTreeInstId:{}. BehaviourCfgId:{}, childUpdateStatus:{}, msg:{}",
                btTask.getBehaviourTree().getId(),
                btTask.getBehaviourTree().getBehaviourCfg().getId(),
                childStatus,
                getAtom().getAtomCfg().getMsg());
        return childStatus;
    }
}
