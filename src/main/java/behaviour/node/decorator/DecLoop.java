package behaviour.node.decorator;

public class DecLoop extends StatusDecorator<config.behaviour.node.DecLoop> {

    public DecLoop(config.behaviour.node.DecLoop atomCfg) {
        super(atomCfg);
    }

    @Override
    protected config.behaviour.node.Node getDecorateNodeCfg() {
        return getAtomCfg().getNode();
    }

    @Override
    public boolean isApplyOnFinishOnly() {
        return getAtomCfg().getValidOnFinish();
    }
}
