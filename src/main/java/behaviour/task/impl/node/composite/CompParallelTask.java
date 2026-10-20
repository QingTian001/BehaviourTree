package behaviour.task.impl.node.composite;

import config.behaviour.Statusenum;
import behaviour.BehaviourStack;
import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.node.Node;
import behaviour.task.CompositeTask;
import behaviour.task.NodeTask;
import behaviour.node.composite.CompParallel;
import behaviour.runtime.composite.CompParallelRuntime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * CompParallel 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 骞惰鎵ц鎵€鏈夊瓙鑺傜偣锛屾牴鎹厤缃喅瀹氭垚鍔?澶辫触绛栫暐
 */
public class CompParallelTask extends CompositeTask implements CompParallelRuntime {
    // 宸插畬鎴愬瓙鑺傜偣鐨勭储寮曢泦鍚堬紙浠?Atom 杩佺Щ锛?
    private final Set<Integer> finishedIndexes = new HashSet<>();
    // 鎴愬姛鑺傜偣璁℃暟锛堜粠 Atom 杩佺Щ锛?
    private int successNodeNum = 0;
    // 姣忎釜瀛愯妭鐐圭殑鎵ц鏍堬紙浠?Atom 杩佺Щ锛?
    private final List<BehaviourStack> behaviourStackList = new ArrayList<>();

    public CompParallelTask(CompParallel atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public CompParallel getAtom() {
        return (CompParallel) super.getAtom();
    }

    @Override
    public void init() {
        for (Node<?> nodeConfig : getAtom().getNodeConfigs()) {
            getNodeTaskList().add(BehaviourFactory.createNodeRuntime(nodeConfig, this, getContext()));
        }
        for (Node<?> nodeConfig : getAtom().getCleanActionConfigs()) {
            getCleanActionTaskList().add(BehaviourFactory.createNodeRuntime(nodeConfig, this, getContext()));
        }
        for (Node<?> nodeConfig : getAtom().getEnterActionConfigs()) {
            getEnterActionTaskList().add(BehaviourFactory.createNodeRuntime(nodeConfig, this, getContext()));
        }
        for (int i = 0; i < getNodeTaskList().size(); i++) {
            behaviourStackList.add(new BehaviourStack());
        }
    }

    /**
     * 鑾峰彇宸插畬鎴愬瓙鑺傜偣鐨勭储寮曢泦鍚?
     */
    @Override
    public Set<Integer> getFinishedIndexes() {
        return finishedIndexes;
    }

    /**
     * 鑾峰彇鎴愬姛鑺傜偣璁℃暟
     */
    @Override
    public int getSuccessNodeNum() {
        return successNodeNum;
    }

    /**
     * 璁剧疆鎴愬姛鑺傜偣璁℃暟
     */
    @Override
    public void setSuccessNodeNum(int num) {
        this.successNodeNum = num;
    }

    /**
     * 鑾峰彇瀛愯妭鐐规墽琛屾爤鍒楄〃
     */
    @Override
    public List<BehaviourStack> getBehaviourStackList() {
        return behaviourStackList;
    }

    /**
     * 妫€鏌ユ寚瀹氱储寮曠殑瀛愯妭鐐规槸鍚﹀凡瀹屾垚
     */
    public boolean isFinished(int index) {
        return finishedIndexes.contains(index);
    }

    /**
     * 鏍囪鎸囧畾绱㈠紩鐨勫瓙鑺傜偣涓哄凡瀹屾垚
     */
    public void markFinished(int index) {
        finishedIndexes.add(index);
    }

}

