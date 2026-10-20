package behaviour.task;

import behaviour.BehaviourContext;
import behaviour.node.SubTree;
import behaviour.runtime.BehaviourTreeRuntime;
import behaviour.runtime.SubTreeRuntime;
import util.StringUtil;

/**
 * SubTree node runtime task.
 * Holds reference to the runtime BehaviourTreeTask of the subtree.
 */
public class SubTreeTask extends NodeTask implements SubTreeRuntime {
    // Runtime subtree instance
    private BehaviourTreeRuntime subTreeTask;

    public SubTreeTask(SubTree atom, BehaviourTask<?> parent, BehaviourContext context) {
        super(atom, parent, context);

    }

    @Override
    public SubTree getAtom() {
        return (SubTree) super.getAtom();
    }

    public BehaviourTreeRuntime getSubTreeTask() {
        return subTreeTask;
    }

    public void setSubTreeTask(BehaviourTreeRuntime subTreeTask) {
        this.subTreeTask = subTreeTask;
    }

    @Override
    public void dump(int tableNum, StringBuilder dumpTo, boolean withCfg) {
        dumpSelf(tableNum, dumpTo, withCfg);
        if (this.subTreeTask != null) {
            String tableStr = StringUtil.getTableString(tableNum);
            dumpTo.append(tableStr).append("{\n");
            asBehaviourTreeTask(this.subTreeTask).dump(tableNum + 1, dumpTo, withCfg);
            dumpTo.append(tableStr).append("}\n");
        }

    }

    private static BehaviourTreeTask asBehaviourTreeTask(BehaviourTreeRuntime runtime) {
        if (!(runtime instanceof BehaviourTreeTask)) {
            throw new RuntimeException("behaviour tree runtime type error:" + runtime);
        }
        return (BehaviourTreeTask) runtime;
    }
}
