package behaviour.runtime.node.decorator;

/**
 * DecLoop 循环状态运行时视图。
 */
public interface DecLoopRuntime extends StatusDecoratorRuntime {
    int getLoopNum();

    void setLoopNum(int loopNum);
}
