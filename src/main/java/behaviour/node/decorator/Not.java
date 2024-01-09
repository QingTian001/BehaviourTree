package behaviour.node.decorator;

import config.behaviour.Statusenum;
import behaviour.BehaviourTree;


public class Not extends Decorator<config.behaviour.node.NodeNot>{
    public Not(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
        super(behaviourTree, nodeCfg);
    }

    @Override
    protected boolean isValidOnFinish() {
        return getNodeCfg().getValidOnFinish();
    }

    @Override
    protected config.behaviour.Node getDecorateNodeCfg() {
        return getNodeCfg().getNode();
    }

    @Override
    protected Statusenum decorate(Statusenum childUpdateStatus) {
        if (childUpdateStatus == Statusenum.BTFAILURE) {
            return Statusenum.BTSUCCESS;
        } else if (childUpdateStatus == Statusenum.BTSUCCESS) {
            return Statusenum.BTFAILURE;
        }
        return Statusenum.BTFAILURE;
    }
}
