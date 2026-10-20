package behaviour.task.impl.node.composite;

import behaviour.BehaviourFactory;
import behaviour.BehaviourContext;
import behaviour.node.Node;
import behaviour.task.CompositeTask;
import behaviour.task.NodeTask;
import behaviour.task.NodeTask;
import behaviour.node.composite.CompSequence;

/**
 * CompSequence 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 椤哄簭鎵ц瀛愯妭鐐癸紝浠讳竴瀛愯妭鐐瑰け璐ュ垯鏁翠綋澶辫触
 */
public class CompSequenceTask extends CompositeTask {

    public CompSequenceTask(CompSequence atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public CompSequence getAtom() {
        return (CompSequence) super.getAtom();
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

