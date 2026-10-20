package behaviour.task.impl.node.decorator;

import behaviour.BehaviourContext;
import behaviour.task.StatusDecoratorTask;
import behaviour.task.NodeTask;
import behaviour.node.decorator.DecNot;

/**
 * DecNot 的运行时任务实现
 * 非装饰器：反转子节点的返回状态
 */
public class DecNotTask extends StatusDecoratorTask {

    public DecNotTask(DecNot atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public DecNot getAtom() {
        return (DecNot) super.getAtom();
    }
}
