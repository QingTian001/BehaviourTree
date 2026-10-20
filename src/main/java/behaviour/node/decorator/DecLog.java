package behaviour.node.decorator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DecLog extends StatusDecorator<config.behaviour.node.DecLog> {
    public static final Logger logger = LogManager.getLogger(DecLog.class);

    public DecLog(config.behaviour.node.DecLog atomCfg) {
        super(atomCfg);
    }

    @Override
    public boolean isApplyOnFinishOnly() {
        return getAtomCfg().getValidOnFinish();
    }

    @Override
    protected config.behaviour.node.Node getDecorateNodeCfg() {
        return getAtomCfg().getNode();
    }
}
