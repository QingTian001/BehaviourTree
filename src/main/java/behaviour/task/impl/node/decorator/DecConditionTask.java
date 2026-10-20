package behaviour.task.impl.node.decorator;

import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.node.IPreConditionTask;
import behaviour.node.decorator.DecCondition;
import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.node.decorator.DecConditionRuntime;
import behaviour.task.ConditionTask;
import behaviour.task.DecoratorTask;
import behaviour.task.NodeTask;
import util.StringUtil;

/**
 * DecCondition 运行时任务实现。
 */
public class DecConditionTask extends DecoratorTask implements DecConditionRuntime, IPreConditionTask {

    private ConditionTask conditionTask;
    private boolean childStarted;

    public DecConditionTask(DecCondition atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public DecCondition getAtom() {
        return (DecCondition) super.getAtom();
    }
    @Override
    public void init() {

        conditionTask = BehaviourFactory.createConditionRuntime(getAtom().getCondition(), null, getContext());
        super.init();
    }

    @Override
    public ConditionRuntime getConditionRuntime() {
        return conditionTask;
    }

    @Override
    public boolean isChildStarted() {
        return childStarted;
    }

    @Override
    public void setChildStarted(boolean childStarted) {
        this.childStarted = childStarted;
    }

    @Override
    public ConditionTask getPreCondition() {
        return conditionTask;
    }

    @Override
    public void dump(int tableNum, StringBuilder dumpTo, boolean withCfg) {
        dumpSelf(tableNum, dumpTo, withCfg);
        int subTableNum = tableNum + 1;
        String tableStr = StringUtil.getTableString(tableNum);
        dumpTo.append(tableStr).append("{\n");
        dumpTo.append(tableStr).append("condition:\n");
        conditionTask.dump(subTableNum, dumpTo, withCfg);
        dumpTo.append(tableStr).append("node:\n");
        dumpDecoratedNode(subTableNum, dumpTo, withCfg);
        dumpTo.append(tableStr).append("}\n");
    }
}
