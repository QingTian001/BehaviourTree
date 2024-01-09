package behaviour.node.decorator;

import config.behaviour.Statusenum;
import behaviour.BehaviourTree;


public class Loop extends Decorator<config.behaviour.node.Loop> {
    private int loopNum = 0;
    public Loop(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
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
    public void reset(boolean recursive) {
        super.reset(recursive);
        loopNum = 0;
    }

    @Override
    protected Statusenum decorate(Statusenum childUpdateStatus) {

        if (getNodeCfg().getLoopNum() < 0) {
            return Statusenum.BTRUNNING;
        }

        if (++loopNum >= getNodeCfg().getLoopNum()) {
            return Statusenum.BTSUCCESS;
        }
        return Statusenum.BTRUNNING;
    }
}
