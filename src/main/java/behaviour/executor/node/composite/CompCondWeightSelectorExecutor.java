package behaviour.executor.node.composite;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import behaviour.BehaviourStack;
import behaviour.executor.node.NodeExecutor;
import behaviour.node.composite.CompCondWeightSelector;
import behaviour.runtime.CompositeRuntime;
import behaviour.runtime.NodeRuntime;
import util.MathUtil;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CompCondWeightSelectorExecutor extends NodeExecutor<CompCondWeightSelector> {
    public CompCondWeightSelectorExecutor(CompCondWeightSelector atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        CompositeRuntime compositeRuntime = (CompositeRuntime) runtime;
        if (compositeRuntime.getPreCondTask() != null
                && !compositeRuntime.getPreCondTask().calculateAndListenEvent()) {
            runtime.log(Level.INFO, "precondition calculate and listen failed");
            return false;
        }
        for (NodeRuntime enterAction : compositeRuntime.getEnterActionTaskList()) {
            Statusenum status = enterAction.update(runtime.getBehaviourTreeRuntime().getBehaviourStack());
            if (status != Statusenum.BTSUCCESS) {
                runtime.log(Level.ERROR,
                        "enter action node failed, status: " + status + " node: " + enterAction);
            }
        }

        CompositeRuntime task = (CompositeRuntime) runtime;
        List<Integer> validWeightList = new ArrayList<>();
        Map<Integer, Integer> validWeightIndex2OriginalIndex = new java.util.HashMap<>();
        int[] weights = getAtom().getWeights();
        var weightConds = getAtom().getWeightConds();
        for (int i = 0; i < weights.length; i++) {
            boolean ret = weightConds[i].calculate(runtime.getContext());
            if (ret) {
                runtime.log(Level.DEBUG, StringUtil.format("valid weight index: %d, weight: %d", i, weights[i]));
                validWeightList.add(weights[i]);
                validWeightIndex2OriginalIndex.put(validWeightList.size() - 1, i);
            }
        }
        if (validWeightList.isEmpty()) {
            return false;
        }
        int index = MathUtil.randomByWeight(validWeightList);
        task.setCurRunningIndex(validWeightIndex2OriginalIndex.get(index));
        return true;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        return doUpdate((CompositeRuntime) runtime, stack, Statusenum.BTRUNNING);
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack, Statusenum childStatus) {
        return doUpdate((CompositeRuntime) runtime, stack, childStatus);
    }

    private Statusenum doUpdate(CompositeRuntime task, BehaviourStack stack, Statusenum childStatus) {
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
        CompositeRuntime compositeRuntime = (CompositeRuntime) runtime;
        compositeRuntime.setCurRunningIndex(0);
        for (NodeRuntime action : compositeRuntime.getCleanActionTaskList()) {
            action.update(runtime.getBehaviourTreeRuntime().getBehaviourStack());
        }
        for (NodeRuntime action : compositeRuntime.getEnterActionTaskList()) {
            action.reset(recursive);
        }
        for (NodeRuntime action : compositeRuntime.getCleanActionTaskList()) {
            action.reset(recursive);
        }
        if (compositeRuntime.getPreCondTask() != null) {
            compositeRuntime.getPreCondTask().reset(recursive);
        }
        for (NodeRuntime node : compositeRuntime.getNodeTaskList()) {
            node.reset(recursive);
        }
    }
}
