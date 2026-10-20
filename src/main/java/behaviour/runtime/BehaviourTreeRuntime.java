package behaviour.runtime;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import behaviour.BehaviourStack;
import behaviour.BehaviourTree;
import behaviour.BlackBoard;

public interface BehaviourTreeRuntime {
    Statusenum update();

    void reset(boolean recursive);

    default void reset() {
        reset(false);
    }

    void interrupt();

    void addSubBehaviourTreeRuntime(BehaviourTreeRuntime subTreeRuntime);

    void removeSubBehaviourTreeRuntime(long subTreeRuntimeId);

    void log(Level level, Class<?> c, String msg, Object... args);

    BehaviourStack getBehaviourStack();

    BehaviourTree getBehaviourTree();

    BlackBoard getBlackBoard();

    long getId();

    /**
     * 获取运行时树实例 id；与配置 id 分开。
     */
    long getInstanceId();

    boolean canPrintLog();

    void setSendDebugMsg(boolean sendDebugMsg);

    boolean canSendDebugMsg();
}
