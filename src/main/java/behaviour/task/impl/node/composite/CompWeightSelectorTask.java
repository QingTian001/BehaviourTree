package behaviour.task.impl.node.composite;

import behaviour.BehaviourFactory;
import behaviour.BehaviourContext;
import behaviour.node.Node;
import behaviour.task.CompositeTask;
import behaviour.task.NodeTask;
import behaviour.node.composite.CompWeightSelector;

/**
 * CompWeightSelector 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 鏉冮噸閫夋嫨鍣細鏍规嵁鏉冮噸闅忔満閫夋嫨涓€涓瓙鑺傜偣鎵ц
 */
public class CompWeightSelectorTask extends CompositeTask {

    public CompWeightSelectorTask(CompWeightSelector atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public CompWeightSelector getAtom() {
        return (CompWeightSelector) super.getAtom();
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

