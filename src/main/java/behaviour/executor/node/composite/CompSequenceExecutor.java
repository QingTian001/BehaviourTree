package behaviour.executor.node.composite;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import behaviour.BehaviourStack;
import behaviour.executor.node.NodeExecutor;
import behaviour.node.composite.CompSequence;
import behaviour.runtime.CompositeRuntime;
import behaviour.runtime.NodeRuntime;

public class CompSequenceExecutor extends NodeExecutor<CompSequence> {
    public CompSequenceExecutor(CompSequence atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        CompositeRuntime compositeRuntime = (CompositeRuntime) runtime;
        if (compositeRuntime.getPreCondTask() != null
                && !compositeRuntime.getPreCondTask().calculateAndListenEvent()) {
            return false;
        }
        for (NodeRuntime enterAction : compositeRuntime.getEnterActionTaskList()) {
            Statusenum status = enterAction.update(runtime.getBehaviourTreeRuntime().getBehaviourStack());
            if (status != Statusenum.BTSUCCESS) {
                runtime.log(Level.ERROR,
                        "enter action node failed, status: " + status + " node: " + enterAction);
            }
        }
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

    private Statusenum doUpdate(CompositeRuntime runtime, BehaviourStack stack, Statusenum childStatus) {
        if (runtime.getNodeTaskList().isEmpty()) {
            return Statusenum.BTSUCCESS;
        }
        int i = runtime.getCurRunningIndex();
        Statusenum runningStatus = childStatus;
        while (true) {
            if (runningStatus == Statusenum.BTFAILURE) {
                return Statusenum.BTFAILURE;
            }
            if (runningStatus == Statusenum.BTRUNNING) {
                NodeRuntime childTask = runtime.getNodeTaskList().get(i);
                runningStatus = childTask.update(stack);
            }
            if (runningStatus == Statusenum.BTRUNNING) {
                runtime.setCurRunningIndex(i);
                return Statusenum.BTRUNNING;
            }
            if (runningStatus == Statusenum.BTFAILURE) {
                return Statusenum.BTFAILURE;
            }
            if (++i >= runtime.getNodeTaskList().size()) {
                return Statusenum.BTSUCCESS;
            }
            runningStatus = Statusenum.BTRUNNING;
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
