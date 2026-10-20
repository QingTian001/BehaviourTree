package behaviour.executor.node.decorator;

import config.behaviour.Statusenum;
import behaviour.node.decorator.DecLoopUtil;
import behaviour.runtime.node.decorator.StatusDecoratorRuntime;

public final class DecLoopUtilExecutor extends StatusDecoratorExecutor<DecLoopUtil> {
    public DecLoopUtilExecutor(DecLoopUtil atom) {
        super(atom);
    }

    @Override
    protected Statusenum applyDecoration(StatusDecoratorRuntime runtime, Statusenum childStatus) {
        if (getAtom().getCond().calculate(runtime.getContext())) {
            return Statusenum.BTSUCCESS;
        }
        return Statusenum.BTRUNNING;
    }
}
