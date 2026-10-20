package behaviour.executor.node.composite;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import behaviour.BehaviourStack;
import behaviour.executor.node.NodeExecutor;
import behaviour.node.composite.CompParamWeightSelector;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.composite.CompParamWeightSelectorRuntime;
import util.MathUtil;
import util.StringUtil;

public final class CompParamWeightSelectorExecutor extends NodeExecutor<CompParamWeightSelector> {
    public CompParamWeightSelectorExecutor(CompParamWeightSelector atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        CompParamWeightSelectorRuntime weightSelectorRuntime = (CompParamWeightSelectorRuntime) runtime;
        if (weightSelectorRuntime.getPreCondTask() != null
                && !weightSelectorRuntime.getPreCondTask().calculateAndListenEvent()) {
            runtime.log(Level.INFO, "precondition calculate and listen failed");
            return false;
        }
        for (NodeRuntime enterAction : weightSelectorRuntime.getEnterActionTaskList()) {
            Statusenum status = enterAction.update(runtime.getBehaviourTreeRuntime().getBehaviourStack());
            if (status != Statusenum.BTSUCCESS) {
                runtime.log(Level.ERROR,
                        "enter action node failed, status: " + status + " node: " + enterAction);
            }
        }

        CompParamWeightSelectorRuntime task = (CompParamWeightSelectorRuntime) runtime;
        for (int i = 0; i < getAtom().getWeightExps().length; i++) {
            task.getWeights()[i] = getAtom().getWeightExps()[i].calculate(runtime.getContext()).intValue();
            runtime.log(Level.INFO, StringUtil.format("weights[{}]={}", i, task.getWeights()[i]));
        }
        task.setCurRunningIndex(MathUtil.randomByWeight(task.getWeights()));
        return true;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        return doUpdate((CompParamWeightSelectorRuntime) runtime, stack, Statusenum.BTRUNNING);
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack, Statusenum childStatus) {
        return doUpdate((CompParamWeightSelectorRuntime) runtime, stack, childStatus);
    }

    private Statusenum doUpdate(CompParamWeightSelectorRuntime task, BehaviourStack stack, Statusenum childStatus) {
        Statusenum runningStatus = childStatus;
        while (true) {
            if (runningStatus == Statusenum.BTSUCCESS) {
                return Statusenum.BTSUCCESS;
            }
            if (runningStatus == Statusenum.BTFAILURE) {
                return Statusenum.BTFAILURE;
            }
            if (runningStatus == Statusenum.BTRUNNING) {
                runningStatus = task.getNodeTaskList().get(task.getCurRunningIndex()).update(stack);
            }
            if (runningStatus == Statusenum.BTRUNNING) {
                return Statusenum.BTRUNNING;
            }
        }
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
        CompParamWeightSelectorRuntime task = (CompParamWeightSelectorRuntime) runtime;
        java.util.Arrays.fill(task.getWeights(), 0);
        task.setCurRunningIndex(0);
        for (NodeRuntime action : task.getCleanActionTaskList()) {
            action.update(runtime.getBehaviourTreeRuntime().getBehaviourStack());
        }
        for (NodeRuntime action : task.getEnterActionTaskList()) {
            action.reset(recursive);
        }
        for (NodeRuntime action : task.getCleanActionTaskList()) {
            action.reset(recursive);
        }
        if (task.getPreCondTask() != null) {
            task.getPreCondTask().reset(recursive);
        }
        for (NodeRuntime node : task.getNodeTaskList()) {
            node.reset(recursive);
        }
    }
}
