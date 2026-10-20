package behaviour.runtime;

import behaviour.BehaviourStack;

import java.util.List;

/**
 * 持有当前行为树内部多个辅助执行栈的运行时节点。
 */
public interface IStackListHolder {
    List<BehaviourStack> getOwnedStacks();
}
