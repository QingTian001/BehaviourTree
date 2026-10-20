package behaviour.executor.node.decorator;

import config.behaviour.Statusenum;
import behaviour.node.decorator.DecFailure;
import behaviour.runtime.node.decorator.StatusDecoratorRuntime;

public final class DecFailureExecutor extends StatusDecoratorExecutor<DecFailure> {
    public DecFailureExecutor(DecFailure atom) {
        super(atom);
    }

    @Override
    protected Statusenum applyDecoration(StatusDecoratorRuntime runtime, Statusenum childStatus) {
        return Statusenum.BTFAILURE;
    }
}
