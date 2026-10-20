package behaviour.task.impl.node.composite;

import behaviour.BehaviourFactory;
import behaviour.BehaviourContext;
import behaviour.node.Node;
import behaviour.task.CompositeTask;
import behaviour.task.NodeTask;
import behaviour.node.composite.CompSelector;

/**
 * CompSelector 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 閫夋嫨鍣細椤哄簭鎵ц瀛愯妭鐐癸紝浠讳竴瀛愯妭鐐规垚鍔熷垯鏁翠綋鎴愬姛锛屽叏閮ㄥけ璐ュ垯鏁翠綋澶辫触
 */
public class CompSelectorTask extends CompositeTask {

    public CompSelectorTask(CompSelector atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public CompSelector getAtom() {
        return (CompSelector) super.getAtom();
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

