package behaviour.executor.node.decorator;

import config.behaviour.Statusenum;
import behaviour.node.decorator.DecSuccess;
import behaviour.runtime.node.decorator.StatusDecoratorRuntime;

public final class DecSuccessExecutor extends StatusDecoratorExecutor<DecSuccess> {
    public DecSuccessExecutor(DecSuccess atom) {
        super(atom);
    }

    @Override
    protected Statusenum applyDecoration(StatusDecoratorRuntime runtime, Statusenum childStatus) {
        return Statusenum.BTSUCCESS;
    }
}
