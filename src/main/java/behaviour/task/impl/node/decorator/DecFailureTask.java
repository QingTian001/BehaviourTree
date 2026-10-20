package behaviour.task.impl.node.decorator;

import behaviour.BehaviourContext;
import behaviour.task.StatusDecoratorTask;
import behaviour.task.NodeTask;
import behaviour.node.decorator.DecFailure;

/**
 * DecFailure 的运行时任务实现
 * 强制失败装饰器：无论子节点返回什么状态，都返回失败
 */
public class DecFailureTask extends StatusDecoratorTask {

    public DecFailureTask(DecFailure atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public DecFailure getAtom() {
        return (DecFailure) super.getAtom();
    }
}
