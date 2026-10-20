package behaviour.executor.node.decorator;

import config.behaviour.Statusenum;
import behaviour.node.decorator.DecNot;
import behaviour.runtime.node.decorator.StatusDecoratorRuntime;

public final class DecNotExecutor extends StatusDecoratorExecutor<DecNot> {
    public DecNotExecutor(DecNot atom) {
        super(atom);
    }

    @Override
    protected Statusenum applyDecoration(StatusDecoratorRuntime runtime, Statusenum childStatus) {
        if (childStatus == Statusenum.BTFAILURE) {
            return Statusenum.BTSUCCESS;
        }
        if (childStatus == Statusenum.BTSUCCESS) {
            return Statusenum.BTFAILURE;
        }
        return Statusenum.BTFAILURE;
    }
}
