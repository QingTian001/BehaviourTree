package behaviour.executor.node.decorator;

import config.behaviour.Statusenum;
import behaviour.BehaviourStack;
import behaviour.executor.node.NodeExecutor;
import behaviour.node.decorator.DecCondition;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.node.decorator.DecConditionRuntime;

/**
 * DecCondition 执行器。
 */
public class DecConditionExecutor extends NodeExecutor<DecCondition> {
    public DecConditionExecutor(DecCondition atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        return ((DecConditionRuntime) runtime).getConditionRuntime().calculateAndListenEvent();
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        DecConditionRuntime decConditionRuntime = (DecConditionRuntime) runtime;
        decConditionRuntime.setChildStarted(true);
        return decConditionRuntime.getDecoratedNodeRuntime().update(stack);
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack, Statusenum childStatus) {
        return childStatus;
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
        DecConditionRuntime decConditionRuntime = (DecConditionRuntime) runtime;
        if (recursive || !decConditionRuntime.isChildStarted()) {
            decConditionRuntime.getDecoratedNodeRuntime().reset(recursive);
        }
        decConditionRuntime.getConditionRuntime().reset(recursive);
        decConditionRuntime.setChildStarted(false);
    }
}
