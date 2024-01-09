package behaviour.node.decorator;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.BehaviourTree;


public class Log extends Decorator<config.behaviour.node.Log>{
    public static final Logger logger = LogManager.getLogger(Log.class);
    public Log(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
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
        BehaviourTree tree = getBehaviourTree();
        logger.info("BehaviourTreeInstId:{}. BehaviourCfgId:{}, childUpdateStauts:{}, msg:{}", tree.getId(), tree.getBehaviourCfg().getId(), childUpdateStatus, getNodeCfg().getMsg());
        return childUpdateStatus;
    }
}
