package behaviour.executor.node.composite;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import behaviour.BehaviourHelper;
import behaviour.BehaviourStack;
import behaviour.executor.node.NodeExecutor;
import behaviour.node.composite.CompParallel;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.composite.CompParallelRuntime;

public class CompParallelExecutor extends NodeExecutor<CompParallel> {
    public CompParallelExecutor(CompParallel atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        CompParallelRuntime task = (CompParallelRuntime) runtime;
        for (NodeRuntime enterAction : task.getEnterActionTaskList()) {
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
        CompParallelRuntime task = (CompParallelRuntime) runtime;
        config.behaviour.node.CompParallel cfg = getAtom().getAtomCfg();

        for (int i = 0; i < task.getNodeTaskList().size(); i++) {
            if (task.getFinishedIndexes().contains(i)) {
                continue;
            }

            NodeRuntime node = task.getNodeTaskList().get(i);
            Statusenum status;
            if (node.getStatus() == Statusenum.BTRUNNING) {
                status = task.getBehaviourStackList().get(i).updateRunningNodeRuntime(task);
            } else {
                status = node.update(task.getBehaviourStackList().get(i));
            }

            if (status == Statusenum.BTFAILURE) {
                if (cfg.getOneFailAllFail()) {
                    interruptRunningNode(task);
                    return Statusenum.BTFAILURE;
                }
            } else if (status == Statusenum.BTSUCCESS) {
                if (cfg.getOneSuccAllSucc()) {
                    interruptRunningNode(task);
                    return Statusenum.BTSUCCESS;
                }
                task.setSuccessNodeNum(task.getSuccessNodeNum() + 1);
            }

            if (BehaviourHelper.isNodeFinished(status)) {
                task.getFinishedIndexes().add(i);
            }
        }

        if (task.getFinishedIndexes().size() == task.getNodeTaskList().size()) {
            return task.getSuccessNodeNum() == task.getNodeTaskList().size()
                    ? Statusenum.BTSUCCESS
                    : Statusenum.BTFAILURE;
        }
        return Statusenum.BTRUNNING;
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
        CompParallelRuntime task = (CompParallelRuntime) runtime;
        for (NodeRuntime action : task.getCleanActionTaskList()) {
            action.update(runtime.getBehaviourTreeRuntime().getBehaviourStack());
        }
        for (NodeRuntime action : task.getEnterActionTaskList()) {
            action.reset(recursive);
        }
        for (NodeRuntime action : task.getCleanActionTaskList()) {
            action.reset(recursive);
        }
        for (NodeRuntime node : task.getNodeTaskList()) {
            node.reset(recursive);
        }
        task.getFinishedIndexes().clear();
        task.setSuccessNodeNum(0);
        for (BehaviourStack childStack : task.getBehaviourStackList()) {
            childStack.reset();
        }
    }

    @Override
    public void onInterrupt(NodeRuntime runtime) {
        interruptRunningNode((CompParallelRuntime) runtime);
    }

    private void interruptRunningNode(CompParallelRuntime task) {
        for (BehaviourStack childStack : task.getBehaviourStackList()) {
            childStack.popAndInterruptRunningNodeAll();
        }
    }
}
