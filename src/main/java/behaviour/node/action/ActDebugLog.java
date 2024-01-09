package behaviour.node.action;

import config.behaviour.Statusenum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.BehaviourStack;
import behaviour.BehaviourTree;

public class ActDebugLog extends Action<config.behaviour.node.ActDebugLog> {
    public static final Logger logger = LogManager.getLogger(ActDebugLog.class);
    public ActDebugLog(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
        super(behaviourTree, nodeCfg);
    }

    @Override
    public void loadFromCfg() {
    }

    @Override
    protected Statusenum internalUpdate(BehaviourStack stack) {
        logger.debug(getNodeCfg().getMsg());
        return Statusenum.BTSUCCESS;
    }

}
