package behaviour.task.impl.node.composite;

import behaviour.BehaviourFactory;
import behaviour.BehaviourContext;
import behaviour.node.Node;
import behaviour.task.CompositeTask;
import behaviour.task.NodeTask;
import behaviour.node.composite.CompCondWeightSelector;

/**
 * CompCondWeightSelector 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 鏉′欢鏉冮噸閫夋嫨鍣細鏍规嵁鏉′欢杩囨护鍚庯紝鎸夋潈閲嶉殢鏈洪€夋嫨瀛愯妭鐐规墽琛?
 */
public class CompCondWeightSelectorTask extends CompositeTask {

    public CompCondWeightSelectorTask(CompCondWeightSelector atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public CompCondWeightSelector getAtom() {
        return (CompCondWeightSelector) super.getAtom();
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
    }
}

