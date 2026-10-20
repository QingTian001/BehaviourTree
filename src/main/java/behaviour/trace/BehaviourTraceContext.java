package behaviour.trace;

import config.behaviour.Statusenum;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.NodeTask;

/**
 * 主树和其子树共享的行为树跟踪上下文。
 */
public final class BehaviourTraceContext {
    private BehaviourTraceListener listener;

    public void setListener(BehaviourTraceListener listener) {
        this.listener = listener;
    }

    public void clearListener(BehaviourTraceListener listener) {
        if (this.listener == listener) {
            this.listener = null;
        }
    }

    public boolean isEnabled() {
        return listener != null;
    }

    public void onTreeNew(BehaviourTreeTask treeTask) {
        if (listener != null) {
            listener.onTreeNew(treeTask);
        }
    }

    public void onSubTreeNew(BehaviourTreeTask treeTask) {
        if (listener != null) {
            listener.onSubTreeNew(treeTask);
        }
    }

    public void onTreeInitialData(BehaviourTreeTask treeTask) {
        if (listener != null) {
            listener.onTreeInitialData(treeTask);
        }
    }

    public void onTreeFinish(BehaviourTreeTask treeTask, Statusenum status) {
        if (listener != null) {
            listener.onTreeFinish(treeTask, status);
        }
    }

    public void onTreeInterrupt(BehaviourTreeTask treeTask, int reason, Throwable exception) {
        if (listener != null) {
            listener.onTreeInterrupt(treeTask, reason, exception);
        }
    }

    public void onTreeStop(BehaviourTreeTask treeTask, int reason, Throwable exception) {
        if (listener != null) {
            listener.onTreeStop(treeTask, reason, exception);
        }
    }

    public void onNodeStatus(BehaviourTreeTask treeTask, NodeTask nodeTask, Statusenum status,
                             boolean listenEventInterrupt) {
        if (listener != null) {
            listener.onNodeStatus(treeTask, nodeTask, status, listenEventInterrupt);
        }
    }

    public void onBlackBoardSet(BehaviourTreeTask treeTask, String bbKey, Object value) {
        if (listener != null) {
            listener.onBlackBoardSet(treeTask, bbKey, value);
        }
    }

    public void onBlackBoardRemove(BehaviourTreeTask treeTask, String bbKey) {
        if (listener != null) {
            listener.onBlackBoardRemove(treeTask, bbKey);
        }
    }

    public void onBlackBoardClear(BehaviourTreeTask treeTask) {
        if (listener != null) {
            listener.onBlackBoardClear(treeTask);
        }
    }
}
