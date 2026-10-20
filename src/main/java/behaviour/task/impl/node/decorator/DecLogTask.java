package behaviour.task.impl.node.decorator;

import behaviour.BehaviourContext;
import behaviour.task.StatusDecoratorTask;
import behaviour.task.NodeTask;
import behaviour.node.decorator.DecLog;

/**
 * DecLog 的运行时任务实现
 * 日志装饰器：记录子节点的执行状态
 */
public class DecLogTask extends StatusDecoratorTask {

    public DecLogTask(DecLog atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public DecLog getAtom() {
        return (DecLog) super.getAtom();
    }
}
