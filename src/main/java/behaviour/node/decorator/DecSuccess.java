package behaviour.node.decorator;

public class DecSuccess extends StatusDecorator<config.behaviour.node.DecSuccess> {

    public DecSuccess(config.behaviour.node.DecSuccess atomCfg) {
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
