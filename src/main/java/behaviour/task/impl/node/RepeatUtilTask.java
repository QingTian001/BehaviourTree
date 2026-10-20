package behaviour.task.impl.node;

import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.task.NodeTask;
import behaviour.node.RepeatUtil;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.RepeatUtilRuntime;
import util.StringUtil;

/**
 * RepeatUtil 鐨勮繍琛屾椂浠诲姟瀹炵幇
 */
public class RepeatUtilTask extends NodeTask implements RepeatUtilRuntime {

    // 瑁呴グ鐨勮妭鐐逛换鍔?
    private NodeRuntime decoratedNodeTask;

    public RepeatUtilTask(RepeatUtil atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public RepeatUtil getAtom() {
        return (RepeatUtil) super.getAtom();
    }

    public void setDecoratedNodeTask(NodeRuntime decoratedNodeTask) {
        this.decoratedNodeTask = decoratedNodeTask;
    }

    public NodeRuntime getDecoratedNodeTask() {
        return decoratedNodeTask;
    }

    @Override
    public void init() {
        NodeRuntime runtime = BehaviourFactory.createNodeRuntime(getAtom().getDecoratedNode(), this, getContext());
        setDecoratedNodeTask(runtime);
    }

    @Override
    public void dump(int tableNum, StringBuilder dumpTo, boolean withCfg) {
        dumpSelf(tableNum, dumpTo, withCfg);
        int subTableNum = tableNum + 1;
        String tableStr = StringUtil.getTableString(tableNum);
        dumpTo.append(tableStr).append("{\n");
        dumpTo.append(tableStr).append("cond: \n");

        getAtom().getCondition().dump(subTableNum, dumpTo, withCfg);
        dumpTo.append(tableStr).append("node:\n");
        asNodeTask(decoratedNodeTask).dump(subTableNum, dumpTo, withCfg);
        dumpTo.append(tableStr).append("}\n");

    }

    private static NodeTask asNodeTask(NodeRuntime runtime) {
        if (!(runtime instanceof NodeTask)) {
            throw new RuntimeException("node runtime type error:" + runtime);
        }
        return (NodeTask) runtime;
    }
}

