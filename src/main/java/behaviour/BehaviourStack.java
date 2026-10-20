package behaviour;

import util.OutObject;
import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import behaviour.node.IPreConditionTask;
import behaviour.runtime.NodeRuntime;
import behaviour.task.ConditionTask;
import behaviour.task.NodeTask;
import behaviour.task.impl.node.decorator.DecConditionTask;
import util.StringUtil;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyao on 2023/12/22 11:07
 *
 * 重构后：BehaviourStack 使用 Task 类型而非 Node 类型
 * - 管理运行中的 NodeTask 栈
 * - 前置条件检查通过 ConditionTask 处理
 */
public final class BehaviourStack {
    // 前置条件任务栈（用于脏检查）
    private final ArrayDeque<ConditionTask> preCondTaskList = new ArrayDeque<>();
    private final Map<ConditionTask, NodeTask> condTask2NodeTaskMap = new HashMap<>();
    // 运行中的节点任务栈
    private final ArrayDeque<NodeTask> stack = new ArrayDeque<>();

    /**
     * 将运行中的节点任务压入栈
     */
    public void pushRunningNode(NodeTask nodeTask) {
        stack.push(nodeTask);
        nodeTask.log(Level.DEBUG, StringUtil.format("stack push. stack:{}, nodeTask:{}", stack, nodeTask.getAtom().getRuntimeId()));
        if (nodeTask instanceof IPreConditionTask) {
            var cond = ((IPreConditionTask)nodeTask).getPreCondition();
            if (cond != null) {
                preCondTaskList.push(cond);
                condTask2NodeTaskMap.put(cond, nodeTask);
            }
        }
    }

    /**
     * 弹出运行中的节点任务
     */
    public NodeTask popRunningNode() {
        NodeTask nodeTask = stack.pop();
        nodeTask.log(Level.DEBUG, StringUtil.format("stack pop. stack:{}, nodeTask:{}", stack, nodeTask.getAtom().getRuntimeId()));
        if (nodeTask instanceof IPreConditionTask) {
            var cond = ((IPreConditionTask)nodeTask).getPreCondition();
            if (cond != null) {
                preCondTaskList.pop();
                condTask2NodeTaskMap.remove(cond);
            }
        }
        return nodeTask;
    }

    /**
     * 弹出并中断节点，直到指定节点
     */
    public void popAndInterruptRunningNodeUtil(NodeTask nodeTask) {
        NodeTask popNode;
        while (true) {
            popNode = popRunningNode();
            popNode.interrupt();
            popNode.reset(false);
            if (popNode == nodeTask) {
                return;
            }
        }
    }

    /**
     * 弹出并中断所有运行中的节点
     */
    public void popAndInterruptRunningNodeAll() {
        NodeTask popNode;
        while (!stack.isEmpty()) {
            popNode = popRunningNode();
            popNode.interrupt();
            popNode.reset(false);
        }
    }

    /**
     * 查看栈顶节点任务
     */
    public NodeTask peekRunningNode() {
        return stack.peek();
    }

    /**
     * 重置栈
     */
    public void reset() {
        stack.clear();
        preCondTaskList.clear();
        condTask2NodeTaskMap.clear();
    }

    /**
     * 检查前置条件（通过 ConditionTask 处理）
     */
    public boolean checkStackPreConditions(OutObject<NodeTask> obj) {
        // 验证前置条件是否可以执行
        for (var it = preCondTaskList.descendingIterator(); it.hasNext(); ) {
            var condTask = it.next();
            if (condTask.isDirty()) {
                boolean ret = condTask.calculateConditionResult();
                condTask.clearDirty();
                if (!ret) {
                    obj.value = condTask2NodeTaskMap.get(condTask);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 回溯父节点更新状态
     */
    private Statusenum backTraceParentNode(NodeTask curNodeTask, NodeTask endParentNodeTask, Statusenum childStatus) {
        Statusenum nodeStatus = childStatus;
        while (true) {
            curNodeTask = (NodeTask) curNodeTask.getParent();
            if (curNodeTask == endParentNodeTask) {
                return nodeStatus;
            }
            nodeStatus = curNodeTask.update(this, nodeStatus);
            if (nodeStatus == Statusenum.BTRUNNING) {
                return Statusenum.BTRUNNING;
            }
        }
    }

    /**
     * 更新运行中的节点
     */
    public Statusenum updateRunningNode(NodeTask endParentNodeTask) {
        OutObject<NodeTask> obj = new OutObject<>();
        boolean ret = this.checkStackPreConditions(obj);

        if (!ret) {
            var nodeTask = obj.value;
            if (nodeTask instanceof DecConditionTask) {
                // listenEvent 让守卫失效时没有普通 update 返回值，先补发特殊 Failure 事件。
                nodeTask.getBehaviourTreeTask().traceListenEventInterrupt(nodeTask);
            }
            // 从当前栈顶一直中断到失效的 DecCondition，并由父节点回溯 Failure。
            this.popAndInterruptRunningNodeUtil(nodeTask);

            if (nodeTask.getParent() != null) {
                return this.backTraceParentNode(nodeTask, endParentNodeTask, Statusenum.BTFAILURE);
            } else {
                return Statusenum.BTFAILURE;
            }
        } else {
            var nodeTask = this.peekRunningNode();
            Statusenum nodeStatus = nodeTask.update(this);
            if (nodeStatus == Statusenum.BTRUNNING) {
                return Statusenum.BTRUNNING;
            } else {
                return this.backTraceParentNode(nodeTask, endParentNodeTask, nodeStatus);
            }
        }
    }

    public Statusenum updateRunningNodeRuntime(NodeRuntime endParentRuntime) {
        return updateRunningNode(asNodeTask(endParentRuntime));
    }

    /**
     * 获取当前运行栈快照，返回值按栈顶到栈底排列。
     */
    public List<NodeTask> getRunningNodes() {
        return new ArrayList<>(stack);
    }

    private static NodeTask asNodeTask(NodeRuntime runtime) {
        if (runtime == null) {
            return null;
        }
        if (!(runtime instanceof NodeTask)) {
            throw new RuntimeException("node runtime type error:" + runtime);
        }
        return (NodeTask) runtime;
    }
}
