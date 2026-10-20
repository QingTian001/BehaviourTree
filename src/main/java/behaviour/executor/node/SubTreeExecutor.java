package behaviour.executor.node;

import config.behaviour.Behaviour;
import config.behaviour.Statusenum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.BehaviourFactory;
import behaviour.BehaviourHelper;
import behaviour.BehaviourStack;
import behaviour.node.SubTree;
import behaviour.runtime.BehaviourTreeRuntime;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.SubTreeRuntime;
import behaviour.task.BehaviourTreeTask;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class SubTreeExecutor extends NodeExecutor<SubTree> {
    private static final Logger logger = LogManager.getLogger(SubTreeExecutor.class);

    public SubTreeExecutor(SubTree atom) {
        super(atom);
    }

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        SubTreeRuntime subTreeRuntime = (SubTreeRuntime) runtime;
        Behaviour subBehaviourCfg = getAtom().getSubBehaviourCfg();
        List<Object> params = new ArrayList<>();
        int subTreeParamCount = subBehaviourCfg.getParams().size();
        int paramBBKeyCount = getAtom().getAtomCfg().getParamBBKeys().size();
        if (subTreeParamCount != paramBBKeyCount) {
            throw new IllegalArgumentException(StringUtil.format(
                    "subTree params size not equal. this treeCfgId:{}, subTreeCfgId:{}, subTreeParamCount:{}, paramBBKeyCount:{}",
                    getBehaviourTreeCfgId(runtime), subBehaviourCfg.getId(), subTreeParamCount, paramBBKeyCount));
        }

        for (int i = 0; i < subTreeParamCount; i++) {
            String bbKey = getAtom().getAtomCfg().getParamBBKeys().get(i);
            Object value = runtime.getContext().getBlackBoard().getValue(bbKey);
            params.add(value);
        }
        BehaviourTreeTask parentTreeTask = runtime.getContext().getBehaviourTreeTask();
        // 子树拥有独立 instanceId，但共享父树 Trace context，并记录创建它的 SubTree 节点。
        BehaviourTreeTask createdSubTree = BehaviourFactory.createBehaviourTreeTask(
                runtime.getContext().getSelf(), runtime.getContext().getHost(), subBehaviourCfg, params,
                parentTreeTask.getTraceContext(), parentTreeTask.getInstanceId(), getAtom().getId());
        createdSubTree.setSendDebugMsg(runtime.getContext().getBehaviourTreeTask().canSendDebugMsg());
        // 子树使用独立的 SUBTREE_NEW 事件，父树实例和父节点信息来自运行时回指字段。
        createdSubTree.traceSubTreeNew();

        BehaviourTreeRuntime parentTreeRuntime = subTreeRuntime.getRootBehaviourTreeRuntime();
        if (parentTreeRuntime != null) {
            parentTreeRuntime.addSubBehaviourTreeRuntime(createdSubTree);
        }
        subTreeRuntime.setSubTreeTask(createdSubTree);
        return true;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        SubTreeRuntime subTreeRuntime = (SubTreeRuntime) runtime;
        BehaviourTreeRuntime btRuntime = subTreeRuntime.getSubTreeTask();
        Behaviour subBehaviourCfg = getAtom().getSubBehaviourCfg();

        log(runtime, Level.DEBUG, "subTree update. this treeCfgId:{}, subTreeCfgId:{}",
                getBehaviourTreeCfgId(runtime), subBehaviourCfg.getId());
        try {
            Statusenum status = btRuntime.update();
            if (BehaviourHelper.isNodeFinished(status)) {
                if (status == Statusenum.BTSUCCESS) {
                    if (!BehaviourHelper.isStringNullOrEmpty(subBehaviourCfg.getReturnValue().getBbKey())
                            && !BehaviourHelper.isStringNullOrEmpty(getAtom().getAtomCfg().getReturnValueBBKey())) {
                        Object returnValue = btRuntime.getBlackBoard().getValue(subBehaviourCfg.getReturnValue().getBbKey());
                        runtime.getContext().getBlackBoard().putValue(getAtom().getAtomCfg().getReturnValueBBKey(), returnValue);
                    }
                } else {
                    log(runtime, Level.INFO, "subTree return failure. this treeCfgId:{}, subTreeCfgId:{}",
                            getBehaviourTreeCfgId(runtime), subBehaviourCfg.getId());
                }
                btRuntime.reset(false);
                BehaviourTreeRuntime parentTreeRuntime = subTreeRuntime.getRootBehaviourTreeRuntime();
                if (parentTreeRuntime != null) {
                    // 子树完成后按实例 id 移除，避免同配置的并行子树互相覆盖。
                    parentTreeRuntime.removeSubBehaviourTreeRuntime(btRuntime.getInstanceId());
                }
            }
            log(runtime, Level.DEBUG, "subTree update result. this treeCfgId:{}, subTreeCfgId:{}, status:{}",
                    getBehaviourTreeCfgId(runtime), subBehaviourCfg.getId(), status);
            return status;
        } catch (Throwable e) {
            logger.error("subTree update error, mainTreeId:{}, subTreeId:{}",
                    getBehaviourTreeCfgId(runtime), subBehaviourCfg.getId());
            logger.error("", e);
            throw e;
        }
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack, Statusenum childStatus) {
        throw new RuntimeException("should not reach here");
    }

    @Override
    public void onInterrupt(NodeRuntime runtime) {
        SubTreeRuntime subTreeRuntime = (SubTreeRuntime) runtime;
        BehaviourTreeRuntime btRuntime = subTreeRuntime.getSubTreeTask();
        if (btRuntime != null) {
            btRuntime.getBehaviourStack().popAndInterruptRunningNodeAll();
        }
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
        SubTreeRuntime subTreeRuntime = (SubTreeRuntime) runtime;
        BehaviourTreeRuntime btRuntime = subTreeRuntime.getSubTreeTask();
        if (btRuntime != null) {
            btRuntime.interrupt();
            BehaviourTreeRuntime parentTreeRuntime = runtime.getRootBehaviourTreeRuntime();
            if (parentTreeRuntime != null) {
                // reset 时同样按子树实例 id 清理父树中的活动子树。
                parentTreeRuntime.removeSubBehaviourTreeRuntime(btRuntime.getInstanceId());
            }
            subTreeRuntime.setSubTreeTask(null);
        }
    }

    private void log(NodeRuntime runtime, Level level, String msg, Object... args) {
        BehaviourTreeRuntime btRuntime = runtime.getRootBehaviourTreeRuntime();
        if (btRuntime != null && btRuntime.canPrintLog()) {
            logger.log(level, this.getClass().getSimpleName() + " " + StringUtil.format(msg, args));
        }
    }

    private int getBehaviourTreeCfgId(NodeRuntime runtime) {
        BehaviourTreeRuntime btRuntime = runtime.getRootBehaviourTreeRuntime();
        return btRuntime != null ? btRuntime.getBehaviourTree().getBehaviourCfg().getId() : -1;
    }
}
