package behaviour.task.impl.node.decorator;

import behaviour.BehaviourContext;
import behaviour.task.StatusDecoratorTask;
import behaviour.task.NodeTask;
import behaviour.node.decorator.DecLoopUtil;

/**
 * DecLoopUtil 的运行时任务实现
 * 条件循环装饰器：只要条件为真就持续循环执行被装饰节点
 */
public class DecLoopUtilTask extends StatusDecoratorTask {

    public DecLoopUtilTask(DecLoopUtil atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public DecLoopUtil getAtom() {
        return (DecLoopUtil) super.getAtom();
    }
}
