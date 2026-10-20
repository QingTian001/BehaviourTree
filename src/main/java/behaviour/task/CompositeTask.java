package behaviour.task;

import behaviour.BehaviourContext;
import behaviour.node.IPreConditionTask;
import behaviour.node.composite.Composite;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.CompositeRuntime;
import behaviour.runtime.NodeRuntime;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite node runtime task.
 * Holds runtime state: child node tasks, preCond task, curRunningIndex.
 */
public class CompositeTask extends NodeTask implements IPreConditionTask, CompositeRuntime {
    // Core child node tasks
    private final List<NodeRuntime> nodeTaskList = new ArrayList<>();
    // Clean action tasks
    private final List<NodeRuntime> cleanActionTaskList = new ArrayList<>();
    // Enter action tasks
    private final List<NodeRuntime> enterActionTaskList = new ArrayList<>();
    // Pre-condition task (only for Composite with preCond)
    private ConditionRuntime preCondTask;
    // Current running child index (runtime state)
    private int curRunningIndex = 0;

    public CompositeTask(Composite<?> atom, BehaviourTask<?> parent, BehaviourContext context) {
        super(atom, parent, context);
    }

    @Override
    public Composite getAtom() {
        return (Composite<?>) super.getAtom();
    }

    public List<NodeRuntime> getNodeTaskList() {
        return nodeTaskList;
    }

    public List<NodeRuntime> getCleanActionTaskList() {
        return cleanActionTaskList;
    }

    public List<NodeRuntime> getEnterActionTaskList() {
        return enterActionTaskList;
    }

    public ConditionRuntime getPreCondTask() {
        return preCondTask;
    }

    public void setPreCondTask(ConditionRuntime preCondTask) {
        this.preCondTask = preCondTask;
    }

    public int getCurRunningIndex() {
        return curRunningIndex;
    }

    public void setCurRunningIndex(int curRunningIndex) {
        this.curRunningIndex = curRunningIndex;
    }

    @Override
    public void dump(int tableNum, StringBuilder dumpTo, boolean withCfg) {
        dumpSelf(tableNum, dumpTo, withCfg);
        int subTableNum = tableNum + 1;
        String tableStr = StringUtil.getTableString(tableNum);
        dumpTo.append(tableStr).append("{\n");
        if (preCondTask != null) {
            dumpTo.append(tableStr).append("preCondition: \n");
            asConditionTask(preCondTask).dump(subTableNum, dumpTo, withCfg);
        }
        dumpTo.append(tableStr).append("nodes:\n");
        for (var node : getNodeTaskList()) {
            dumpNodeRuntime(node, subTableNum, dumpTo, withCfg);
        }
        if (!enterActionTaskList.isEmpty()) {
            dumpTo.append(tableStr).append("enter actions:\n");
            for (var node : enterActionTaskList) {
                dumpNodeRuntime(node, subTableNum, dumpTo, withCfg);
            }
        }
        if (!cleanActionTaskList.isEmpty()) {
            dumpTo.append(tableStr).append("clean actions:\n");
            for (var node : cleanActionTaskList) {
                dumpNodeRuntime(node, subTableNum, dumpTo, withCfg);
            }
        }
        dumpTo.append(tableStr).append("}\n");
    }

    @Override
    public ConditionTask getPreCondition() {
        return asConditionTask(preCondTask);
    }

    private static void dumpNodeRuntime(NodeRuntime runtime, int tableNum, StringBuilder dumpTo, boolean withCfg) {
        asNodeTask(runtime).dump(tableNum, dumpTo, withCfg);
    }

    private static NodeTask asNodeTask(NodeRuntime runtime) {
        if (!(runtime instanceof NodeTask)) {
            throw new RuntimeException("node runtime type error:" + runtime);
        }
        return (NodeTask) runtime;
    }

    private static ConditionTask asConditionTask(ConditionRuntime runtime) {
        if (runtime == null) {
            return null;
        }
        if (!(runtime instanceof ConditionTask)) {
            throw new RuntimeException("condition runtime type error:" + runtime);
        }
        return (ConditionTask) runtime;
    }
}
