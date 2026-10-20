package behaviour.executor.node.decorator;

import config.behaviour.Statusenum;
import behaviour.node.decorator.DecLoop;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.node.decorator.DecLoopRuntime;
import behaviour.runtime.node.decorator.StatusDecoratorRuntime;

public final class DecLoopExecutor extends StatusDecoratorExecutor<DecLoop> {
    public DecLoopExecutor(DecLoop atom) {
        super(atom);
    }

    @Override
    protected Statusenum applyDecoration(StatusDecoratorRuntime runtime, Statusenum childStatus) {
        DecLoopRuntime loopRuntime = (DecLoopRuntime) runtime;
        if (getAtom().getAtomCfg().getLoopNum() < 0) {
            return Statusenum.BTRUNNING;
        }
        if (loopRuntime.getLoopNum() >= getAtom().getAtomCfg().getLoopNum()) {
            return Statusenum.BTSUCCESS;
        }
        loopRuntime.setLoopNum(loopRuntime.getLoopNum() + 1);
        return Statusenum.BTRUNNING;
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
        super.reset(runtime, recursive);
        ((DecLoopRuntime) runtime).setLoopNum(0);
    }
}
