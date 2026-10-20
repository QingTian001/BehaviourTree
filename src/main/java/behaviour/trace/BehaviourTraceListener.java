package behaviour.trace;

import config.behaviour.Statusenum;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.NodeTask;

/**
 * 行为树运行时的精确跟踪出口。
 *
 * <p>该接口只接收运行时生命周期事件，不负责保存或发送协议。</p>
 */
public interface BehaviourTraceListener {
    void onTreeNew(BehaviourTreeTask treeTask);

    void onSubTreeNew(BehaviourTreeTask treeTask);

    void onTreeInitialData(BehaviourTreeTask treeTask);

    void onTreeFinish(BehaviourTreeTask treeTask, Statusenum status);

    void onTreeInterrupt(BehaviourTreeTask treeTask, int reason, Throwable exception);

    void onTreeStop(BehaviourTreeTask treeTask, int reason, Throwable exception);

    void onNodeStatus(BehaviourTreeTask treeTask, NodeTask nodeTask, Statusenum status,
                      boolean listenEventInterrupt);

    void onBlackBoardSet(BehaviourTreeTask treeTask, String bbKey, Object value);

    void onBlackBoardRemove(BehaviourTreeTask treeTask, String bbKey);

    void onBlackBoardClear(BehaviourTreeTask treeTask);
}
