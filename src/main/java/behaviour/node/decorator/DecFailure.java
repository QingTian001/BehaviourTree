package behaviour.node.decorator;

public class DecFailure extends StatusDecorator<config.behaviour.node.DecFailure> {

    public DecFailure(config.behaviour.node.DecFailure atomCfg) {
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
