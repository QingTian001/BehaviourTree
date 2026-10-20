package behaviour.runtime;

import behaviour.BehaviourStack;

/**
 * 持有当前行为树内部一个辅助执行栈的运行时节点。
 */
public interface IStackHolder {
    BehaviourStack getOwnedStack();
}
