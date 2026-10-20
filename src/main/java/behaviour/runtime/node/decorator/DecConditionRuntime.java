package behaviour.runtime.node.decorator;


import behaviour.runtime.ConditionRuntime;
import behaviour.runtime.DecoratorRuntime;

/**
 * DecCondition 运行时视图。
 */
public interface DecConditionRuntime extends DecoratorRuntime {
    ConditionRuntime getConditionRuntime();

    boolean isChildStarted();

    void setChildStarted(boolean childStarted);
}
