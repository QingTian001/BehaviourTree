package behaviour.task.impl.node.composite;

import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.node.Node;
import behaviour.node.composite.CompParamWeightSelector;
import behaviour.runtime.composite.CompParamWeightSelectorRuntime;
import behaviour.task.CompositeTask;
import behaviour.task.NodeTask;

/**
 * CompParamWeightSelector 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 鍙傛暟鏉冮噸閫夋嫨鍣細鏍规嵁鍙傛暟琛ㄨ揪寮忚绠楃殑鏉冮噸鍊奸殢鏈洪€夋嫨瀛愯妭鐐规墽琛?
 */
public class CompParamWeightSelectorTask extends CompositeTask implements CompParamWeightSelectorRuntime {
    // 鏉冮噸鍒楄〃
    private int[] weights;

    public CompParamWeightSelectorTask(CompParamWeightSelector atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public CompParamWeightSelector getAtom() {
        return (CompParamWeightSelector) super.getAtom();
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
        newWeights();
    }

    @Override
    public int[] getWeights() {
        return weights;
    }

    public void newWeights() {
        weights = new int[getAtom().getWeightExps().length];
    }

}

