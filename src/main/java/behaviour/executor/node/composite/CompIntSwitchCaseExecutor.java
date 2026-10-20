package behaviour.executor.node.composite;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import behaviour.BehaviourStack;
import behaviour.executor.node.NodeExecutor;
import behaviour.node.composite.CompIntSwitchCase;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.composite.CompIntSwitchCaseRuntime;

public final class CompIntSwitchCaseExecutor extends NodeExecutor<CompIntSwitchCase> {
    public CompIntSwitchCaseExecutor(CompIntSwitchCase atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        CompIntSwitchCaseRuntime task = (CompIntSwitchCaseRuntime) runtime;
        if (task.getPreCondTask() != null && !task.getPreCondTask().calculateAndListenEvent()) {
            runtime.log(Level.INFO, "precondition calculate and listen failed");
            return false;
        }
        for (NodeRuntime enterAction : task.getEnterActionTaskList()) {
            Statusenum status = enterAction.update(runtime.getBehaviourTreeRuntime().getBehaviourStack());
            if (status != Statusenum.BTSUCCESS) {
                runtime.log(Level.ERROR,
                        "enter action node failed, status: " + status + " node: " + enterAction);
            }
        }
        int value = getAtom().getCaseValueExpression().calculate(runtime.getContext()).intValue();
        task.setValue(value);

        int findIndex = -1;
        int[] cases = getAtom().getCases();
        for (int i = 0; i < cases.length; i++) {
            if (value == cases[i]) {
                findIndex = i;
                break;
            }
        }
        task.setCurRunningCaseNum(findIndex);
        return true;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        return doUpdate((CompIntSwitchCaseRuntime) runtime, stack, Statusenum.BTRUNNING);
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack, Statusenum childStatus) {
        return doUpdate((CompIntSwitchCaseRuntime) runtime, stack, childStatus);
    }

    private Statusenum doUpdate(CompIntSwitchCaseRuntime task, BehaviourStack stack, Statusenum childStatus) {
        Statusenum runningStatus = childStatus;
        while (true) {
            if (runningStatus == Statusenum.BTSUCCESS) {
                return Statusenum.BTSUCCESS;
            }
            if (runningStatus == Statusenum.BTFAILURE) {
                return Statusenum.BTFAILURE;
            }
            if (runningStatus == Statusenum.BTRUNNING) {
                NodeRuntime node = task.useDefaultCase()
                        ? task.getDefaultCaseTask()
                        : task.getNodeTaskList().get(task.getCurRunningCaseNum());
                runningStatus = node.update(stack);
            }
            if (runningStatus == Statusenum.BTRUNNING) {
                return Statusenum.BTRUNNING;
            }
        }
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
        CompIntSwitchCaseRuntime task = (CompIntSwitchCaseRuntime) runtime;
        task.setCurRunningCaseNum(0);
        task.setValue(0);
        if (task.getDefaultCaseTask() != null) {
            task.getDefaultCaseTask().reset(recursive);
        }
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
