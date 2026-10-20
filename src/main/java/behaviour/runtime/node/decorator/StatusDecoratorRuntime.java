package behaviour.runtime.node.decorator;

import behaviour.BehaviourStack;
import behaviour.runtime.DecoratorRuntime;
import behaviour.runtime.IStackHolder;

/**
 * 使用私有子栈监督被装饰节点状态的运行时视图。
 */
public interface StatusDecoratorRuntime extends DecoratorRuntime, IStackHolder {
    BehaviourStack getDecorateNodeStack();

    @Override
    default BehaviourStack getOwnedStack() {
        return getDecorateNodeStack();
    }
}
