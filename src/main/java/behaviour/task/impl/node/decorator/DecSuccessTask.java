package behaviour.task.impl.node.decorator;

import behaviour.BehaviourContext;
import behaviour.task.StatusDecoratorTask;
import behaviour.task.NodeTask;
import behaviour.node.decorator.DecSuccess;

/**
 * DecSuccess 的运行时任务实现
 * 强制成功装饰器：无论子节点返回什么状态，都返回成功
 */
public class DecSuccessTask extends StatusDecoratorTask {

    public DecSuccessTask(DecSuccess atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public DecSuccess getAtom() {
        return (DecSuccess) super.getAtom();
    }
}
