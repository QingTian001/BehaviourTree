package behaviour.executor.condition;
import behaviour.BehaviourContext;
import behaviour.condition.NULL;
import behaviour.runtime.ConditionRuntime;
public final class NULLExecutor extends ConditionExecutor<NULL> {
    public NULLExecutor(NULL atom) { super(atom); }
    @Override public boolean calculate(BehaviourContext context) { return true; }
    @Override public boolean calculateAndListenEvent(ConditionRuntime runtime) { return true; }
}
