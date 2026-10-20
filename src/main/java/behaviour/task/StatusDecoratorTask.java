package behaviour.task;

import behaviour.BehaviourContext;
import behaviour.BehaviourStack;
import behaviour.node.decorator.StatusDecorator;
import behaviour.runtime.node.decorator.StatusDecoratorRuntime;

/**
 * 状态装饰节点运行时任务基类，持有子节点的独立执行栈。
 */
public abstract class StatusDecoratorTask extends DecoratorTask implements StatusDecoratorRuntime {
    private final BehaviourStack decorateNodeStack = new BehaviourStack();

    protected StatusDecoratorTask(StatusDecorator<?> atom, BehaviourTask<?> parent, BehaviourContext context) {
        super(atom, parent, context);
    }

    @Override
    public StatusDecorator<?> getAtom() {
        return (StatusDecorator<?>) super.getAtom();
    }

    @Override
    public BehaviourStack getDecorateNodeStack() {
        return decorateNodeStack;
    }
}
