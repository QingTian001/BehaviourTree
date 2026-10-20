package behaviour.task;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.BehaviourHost;
import behaviour.GEntity;
import behaviour.BehaviourContext;
import behaviour.BehaviourHelper;
import behaviour.BehaviourStack;
import behaviour.BlackBoard;
import behaviour.runtime.BehaviourTreeRuntime;
import behaviour.runtime.IStackHolder;
import behaviour.runtime.IStackListHolder;
import behaviour.runtime.SubTreeRuntime;
import behaviour.trace.BehaviourTraceContext;
import behaviour.trace.BehaviourTraceListener;
import util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 行为树运行时任务
 * 包含所有运行时状态：status, blackBoard, subTreeTasks, runningStack等
 * 从 BehaviourTree 迁移过来的运行时逻辑
 */
public class BehaviourTreeTask implements BehaviourTreeRuntime {
    public static final Logger logger = LogManager.getLogger(BehaviourTreeTask.class);
    private static final AtomicLong INSTANCE_ID_GENERATOR = new AtomicLong();

    // 静态配置引用
    private final behaviour.BehaviourTree behaviourTree;

    // 运行时状态（从 BehaviourTree 迁移）
    private Statusenum status = Statusenum.BTINVALID;
    private final BlackBoard blackBoard;
    private final Map<Long, BehaviourTreeTask> subTreeTasks = new LinkedHashMap<>();
    private final BehaviourStack runningStack = new BehaviourStack();
    private NodeTask rootNodeTask = null;
    // instanceId 区分同一配置创建出的不同运行时树；getId() 仍然表示配置 id。
    private final long instanceId = INSTANCE_ID_GENERATOR.incrementAndGet();
    // 子树通过这两个字段回指创建它的父树和 SubTree 节点，主树均为 0。
    private final long parentTreeInstanceId;
    private final long parentNodeId;
    // 主树与子树共享同一个 Trace context，保证事件发送到同一订阅流。
    private BehaviourTraceContext traceContext;
    // 只记录客户端已经看到的节点可观察状态，不向客户端发送 BTINVALID。
    private final Map<Long, TraceNodeState> reportedNodeStatuses = new HashMap<>();
    // 统计当前树实例的正常完成轮数和异常次数。
    private int executeCount;
    private int errorCount;

    // 日志控制
    private boolean logPrint = false;
    private boolean sendDebugMsg = false;
    // AIC dump监控强制开启调试消息通知
    private boolean dumpDebugMsg = false;

    private final BehaviourContext context;

    private static final class TraceNodeState {
        private final Statusenum status;
        private final long subTreeInstanceId;
        private final boolean listenEventInterrupt;

        private TraceNodeState(Statusenum status, long subTreeInstanceId, boolean listenEventInterrupt) {
            this.status = status;
            this.subTreeInstanceId = subTreeInstanceId;
            this.listenEventInterrupt = listenEventInterrupt;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof TraceNodeState)) {
                return false;
            }
            TraceNodeState that = (TraceNodeState)o;
            return subTreeInstanceId == that.subTreeInstanceId
                    && listenEventInterrupt == that.listenEventInterrupt
                    && status == that.status;
        }

        @Override
        public int hashCode() {
            return Objects.hash(status, subTreeInstanceId, listenEventInterrupt);
        }
    }

    public BehaviourTreeTask(behaviour.BehaviourTree behaviourTree, GEntity entity, BehaviourHost host) {
        this(behaviourTree, entity, host, new BehaviourTraceContext(), 0, 0);
    }

    public BehaviourTreeTask(behaviour.BehaviourTree behaviourTree, GEntity entity, BehaviourHost host,
                             BehaviourTraceContext traceContext, long parentTreeInstanceId, long parentNodeId) {
        this.behaviourTree = behaviourTree;
        this.traceContext = traceContext;
        this.parentTreeInstanceId = parentTreeInstanceId;
        this.parentNodeId = parentNodeId;
        this.blackBoard = new BlackBoard(entity, this);
        this.context = new BehaviourContext(entity, blackBoard, this, host);
    }

    /**
     * Get the behaviour context for this task
     */
    public BehaviourContext getContext() {
        return context;
    }

    /**
     * Initialize parameters from the behaviour config
     */
    public void initParams(List<Object> params) {
        try {
            var behaviourCfg = behaviourTree.getBehaviourCfg();
            for (int i = 0; i < behaviourCfg.getParams().size(); i++) {
                blackBoard.putValue(behaviourCfg.getParams().get(i).getBbKey(), params.get(i));
            }
        } catch (Exception e) {
            logger.error("initParams error. entityInstId:{}, entityType:{}, entityCfgId:{}, btCfgId:{}, params:{}",
                    getEntity().getInstanceId(), 0,
                    0,
                    behaviourTree.getBehaviourCfg().getId(), params);
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化根节点任务
     * 在 BehaviourTree 创建 Task 后调用
     */
    public void initRootNodeTask(NodeTask rootNodeTask) {
        this.rootNodeTask = rootNodeTask;
    }

    public behaviour.BehaviourTree getBehaviourTree() {
        return behaviourTree;
    }

    public GEntity getEntity() {
        return blackBoard.getEntity();
    }

    public BehaviourHost getHost() { return context.getHost(); }

    public Statusenum getStatus() {
        return status;
    }

    public BlackBoard getBlackBoard() {
        return blackBoard;
    }

    public BehaviourStack getBehaviourStack() {
        return runningStack;
    }

    /**
     * 收集当前树实例所有运行中的节点，包括装饰器私有栈和并行分支栈。
     * 子树由 Trace manager 按树实例单独收集。
     */
    public List<NodeTask> collectTraceRunningNodes() {
        ArrayList<NodeTask> runningNodes = new ArrayList<>();
        var visited = Collections.newSetFromMap(new IdentityHashMap<NodeTask, Boolean>());
        collectTraceRunningNodes(runningStack, runningNodes, visited);
        return runningNodes;
    }

    private static void collectTraceRunningNodes(BehaviourStack stack,
                                                 List<NodeTask> runningNodes,
                                                 Set<NodeTask> visited) {
        for (NodeTask nodeTask : stack.getRunningNodes()) {
            if (!visited.add(nodeTask)) {
                continue;
            }
            runningNodes.add(nodeTask);
            if (nodeTask instanceof IStackHolder) {
                collectTraceRunningNodes(
                        ((IStackHolder)nodeTask).getOwnedStack(), runningNodes, visited);
            }
            if (nodeTask instanceof IStackListHolder) {
                for (BehaviourStack branchStack : ((IStackListHolder)nodeTask).getOwnedStacks()) {
                    collectTraceRunningNodes(branchStack, runningNodes, visited);
                }
            }
        }
    }

    public long getId() {
        return behaviourTree.getId();
    }

    public long getInstanceId() {
        return instanceId;
    }

    public long getParentTreeInstanceId() {
        return parentTreeInstanceId;
    }

    public long getParentNodeId() {
        return parentNodeId;
    }

    public int getExecuteCount() {
        return executeCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public BehaviourTraceContext getTraceContext() {
        return traceContext;
    }

    public void setTraceContext(BehaviourTraceContext traceContext) {
        if (traceContext == null) {
            throw new IllegalArgumentException("traceContext is null");
        }
        this.traceContext = traceContext;
        for (BehaviourTreeTask subTreeTask : subTreeTasks.values()) {
            subTreeTask.setTraceContext(traceContext);
        }
    }

    public void setTraceListener(BehaviourTraceListener listener) {
        traceContext.setListener(listener);
    }

    public void clearTraceListener(BehaviourTraceListener listener) {
        traceContext.clearListener(listener);
    }

    public void addSubBehaviourTreeTask(BehaviourTreeTask subTreeTask) {
        subTreeTasks.put(subTreeTask.getInstanceId(), subTreeTask);
    }

    @Override
    public void addSubBehaviourTreeRuntime(BehaviourTreeRuntime subTreeRuntime) {
        if (!(subTreeRuntime instanceof BehaviourTreeTask)) {
            throw new RuntimeException("subTreeRuntime type error:" + subTreeRuntime);
        }
        addSubBehaviourTreeTask((BehaviourTreeTask) subTreeRuntime);
    }

    public void removeSubBehaviourTreeTask(long subTreeTaskId) {
        subTreeTasks.remove(subTreeTaskId);
    }

    @Override
    public void removeSubBehaviourTreeRuntime(long subTreeRuntimeId) {
        removeSubBehaviourTreeTask(subTreeRuntimeId);
    }

    public Collection<BehaviourTreeTask> getSubBehaviourTreeTasks() {
        return new ArrayList<>(subTreeTasks.values());
    }

    /**
     * 更新行为树
     */
    public Statusenum update() {
        updateNode();
        Statusenum rStatus = status;
        if (BehaviourHelper.isNodeFinished(status)) {
            // finish 会在 reset 前发送 TREE_FINISH，确保客户端拿到 root 的真实终态。
            finish(rStatus);
        }
        return rStatus;
    }

    private void updateNode() {
        if (status == Statusenum.BTRUNNING) {
            status = runningStack.updateRunningNode(null);
        } else {
            status = rootNodeTask.update(runningStack);
        }
    }

    private void finish(Statusenum finishStatus) {
        log(Level.DEBUG, this.getClass(), "finish behaviourTree. status:{}, entityInstId:{}, btCfgId:{}. ",
                status, getEntity().getInstanceId(), behaviourTree.getBehaviourCfg().getId());
        if (parentTreeInstanceId == 0) {
            executeCount++;
            // 子树只通过 SubTree 节点和内部节点上报，不产生独立树生命周期事件。
            traceContext.onTreeFinish(this, finishStatus);
        }
        reset(false);
    }

    /**
     * Reset with recursive flag
     */
    public void reset(boolean recursive) {
        // 即使不递归重置，也必须先释放活动分支和子树的操作及监听。
        runningStack.popAndInterruptRunningNodeAll();
        for (BehaviourTreeTask subTree : new ArrayList<>(subTreeTasks.values())) {
            subTree.interrupt();
        }
        subTreeTasks.clear();
        runningStack.reset();
        reportedNodeStatuses.clear();
        status = Statusenum.BTINVALID;

        if (recursive && rootNodeTask != null) {
            rootNodeTask.reset(true);
        }
    }

    /**
     * Reset without recursive (default)
     */
    public void reset() {
        reset(false);
    }

    public void clearBlackBoard() {
        blackBoard.clear();
    }

    public void recordError(Throwable exception) {
        // AiManager 捕获异常后调用；错误次数达到阈值时由 AiManager 发送 TREE_STOP。
        errorCount++;
    }

    public void traceNodeStatus(NodeTask nodeTask, Statusenum nodeStatus, boolean listenEventInterrupt) {
        if (!traceContext.isEnabled()) {
            return;
        }
        long nodeId = nodeTask.getAtom().getId();
        if (listenEventInterrupt) {
            // DecCondition 失效没有经过普通 update，单独标记后让客户端按拓扑清理后代。
            reportedNodeStatuses.remove(nodeId);
            traceContext.onNodeStatus(this, nodeTask, Statusenum.BTFAILURE, true);
            return;
        }
        TraceNodeState currentState = new TraceNodeState(
                nodeStatus, getSubTreeInstanceId(nodeTask), false);
        TraceNodeState lastState = reportedNodeStatuses.get(nodeId);
        if (BehaviourHelper.isNodeFinished(nodeStatus) || !currentState.equals(lastState)) {
            // Running 只在首次进入或状态变化时发送；终态每轮都发送。
            traceContext.onNodeStatus(this, nodeTask, nodeStatus, false);
        }
        if (BehaviourHelper.isNodeFinished(nodeStatus)) {
            reportedNodeStatuses.remove(nodeId);
        } else {
            reportedNodeStatuses.put(nodeId, currentState);
        }
    }

    private static long getSubTreeInstanceId(NodeTask nodeTask) {
        if (!(nodeTask instanceof SubTreeRuntime)) {
            return 0;
        }
        BehaviourTreeRuntime subTreeRuntime = ((SubTreeRuntime)nodeTask).getSubTreeTask();
        return subTreeRuntime == null ? 0 : subTreeRuntime.getInstanceId();
    }

    public void traceListenEventInterrupt(NodeTask nodeTask) {
        traceNodeStatus(nodeTask, Statusenum.BTFAILURE, true);
    }

    public void traceNodeReset(NodeTask nodeTask) {
        if (traceContext.isEnabled()) {
            reportedNodeStatuses.remove(nodeTask.getAtom().getId());
        }
    }

    public void traceBlackBoardSet(String bbKey, Object value) {
        traceContext.onBlackBoardSet(this, bbKey, value);
    }

    public void traceBlackBoardRemove(String bbKey) {
        traceContext.onBlackBoardRemove(this, bbKey);
    }

    public void traceBlackBoardClear() {
        traceContext.onBlackBoardClear(this);
    }

    public void traceTreeNew() {
        traceContext.onTreeNew(this);
    }

    public void traceSubTreeNew() {
        traceContext.onSubTreeNew(this);
    }

    public void traceTreeInterrupt(int reason, Throwable exception) {
        traceContext.onTreeInterrupt(this, reason, exception);
    }

    public void traceTreeStop(int reason, Throwable exception) {
        traceContext.onTreeStop(this, reason, exception);
    }

    public void interrupt() {
        reset(false);
    }

    public void setLogPrint(boolean logPrint) {
        this.logPrint = logPrint;
        for (BehaviourTreeTask subTreeTask : subTreeTasks.values()) {
            subTreeTask.setLogPrint(logPrint);
        }
    }

    @Override
    public void setSendDebugMsg(boolean sendDebugMsg) {
        this.sendDebugMsg = sendDebugMsg;
        for (BehaviourTreeTask subTreeTask : subTreeTasks.values()) {
            subTreeTask.setSendDebugMsg(sendDebugMsg);
        }
    }


    /**
     * 设置dump监控强制开启调试消息通知, 会同步到子行为树
     */
    public void setDumpDebugMsg(boolean dumpDebugMsg) {
        this.dumpDebugMsg = dumpDebugMsg;
        for (BehaviourTreeTask subTreeTask : subTreeTasks.values()) {
            subTreeTask.setDumpDebugMsg(dumpDebugMsg);
        }
    }

    public boolean getLogPrint() {
        return logPrint;
    }

    public boolean canPrintLog() {
        return logPrint;
    }

    @Override
    public boolean canSendDebugMsg() {
        return sendDebugMsg || dumpDebugMsg;
    }

    public void log(Level level, Class<?> c, String msg, Object... args) {
        if (canPrintLog()) {
            StringBuilder sb = new StringBuilder();
            sb.append(c.getSimpleName());
            sb.append(" ");
            sb.append(msg);
            logger.log(level, sb.toString(), args);
        }
    }

    /**
     * Dump 配置信息（委托给 BehaviourTree）
     */

    public String dump(boolean withCfg) {
        StringBuilder sb = new StringBuilder();
        dump(0, sb, withCfg);
        String dump = sb.toString();
        log(Level.INFO, this.getClass(),"dump behaviourTree. entityInstId:{}, entityType:{}. \n{}", getEntity().getInstanceId(), 0, dump);
        return dump;
    }

    // 子树dump时会调用
    public void dump(int tableNum, StringBuilder dumpTo, boolean withCfg){
        if (tableNum == 0) {
            dumpTo.append("BehaviourTree CfgId:").append(this.getBehaviourTree().getId()).append("\n");
        } else {
            dumpTo.append(StringUtil.getTableString(tableNum)).append("sub BehaviourTree CfgId:").append(this.getBehaviourTree().getId()).append("\n");
        }
        blackBoard.dump(tableNum, dumpTo);
        rootNodeTask.dump(tableNum, dumpTo, withCfg);
    }
}
