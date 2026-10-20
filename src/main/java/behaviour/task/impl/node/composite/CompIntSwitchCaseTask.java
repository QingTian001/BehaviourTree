package behaviour.task.impl.node.composite;

import behaviour.BehaviourFactory;
import behaviour.BehaviourContext;
import behaviour.node.Node;
import behaviour.task.CompositeTask;
import behaviour.task.NodeTask;
import behaviour.node.composite.CompIntSwitchCase;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.composite.CompIntSwitchCaseRuntime;

/**
 * CompIntSwitchCase 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 鏁存暟SwitchCase锛氭牴鎹〃杈惧紡鍊煎尮閰峜ase锛屾墽琛屽搴旂殑瀛愯妭鐐?
 */
public class CompIntSwitchCaseTask extends CompositeTask implements CompIntSwitchCaseRuntime {
    // 褰撳墠杩愯鐨刢ase缂栧彿锛?1琛ㄧず浣跨敤default锛屼粠 Atom 杩佺Щ杩囨潵鐨勮繍琛屾椂鐘舵€侊級
    private int curRunningCaseNum = 0;
    // 琛ㄨ揪寮忚绠楃殑鍊硷紙杩愯鏃剁姸鎬侊級
    private int value = 0;
    // default case浠诲姟
    private NodeRuntime defaultCaseTask;

    public CompIntSwitchCaseTask(CompIntSwitchCase atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public CompIntSwitchCase getAtom() {
        return (CompIntSwitchCase) super.getAtom();
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
        if (getAtom().getPreCondConfig() != null) {
            setPreCondTask(BehaviourFactory.createConditionRuntime(getAtom().getPreCondConfig(), null, getContext()));
        }
        if (getAtom().getDefaultCase() != null) {
            setDefaultCaseTask(BehaviourFactory.createNodeRuntime(getAtom().getDefaultCase(), this, getContext()));
        }
    }

    /**
     * 鑾峰彇褰撳墠杩愯鐨刢ase缂栧彿
     */
    @Override
    public int getCurRunningCaseNum() {
        return curRunningCaseNum;
    }

    /**
     * 璁剧疆褰撳墠杩愯鐨刢ase缂栧彿
     */
    @Override
    public void setCurRunningCaseNum(int num) {
        this.curRunningCaseNum = num;
    }

    /**
     * 鑾峰彇琛ㄨ揪寮忚绠楃殑鍊?
     */
    @Override
    public int getValue() {
        return value;
    }

    /**
     * 璁剧疆琛ㄨ揪寮忚绠楃殑鍊?
     */
    @Override
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * 鏄惁浣跨敤default case
     */
    @Override
    public boolean useDefaultCase() {
        return curRunningCaseNum == -1;
    }

    /**
     * 璁剧疆default case浠诲姟
     */
    @Override
    public void setDefaultCaseTask(NodeRuntime runtime) {
        this.defaultCaseTask = runtime;
    }

    /**
     * 鑾峰彇default case浠诲姟
     */
    @Override
    public NodeRuntime getDefaultCaseTask() {
        return defaultCaseTask;
    }
}

