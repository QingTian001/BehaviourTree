package behaviour.runtime;

/**
 * 持有另一棵行为树运行时实例的节点，不属于当前树的辅助执行栈持有者。
 */
public interface SubTreeRuntime extends NodeRuntime {
    BehaviourTreeRuntime getSubTreeTask();

    void setSubTreeTask(BehaviourTreeRuntime subTreeRuntime);
}
