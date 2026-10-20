package behaviour.node.decorator;

public class DecNot extends StatusDecorator<config.behaviour.node.DecNot> {

    public DecNot(config.behaviour.node.DecNot atomCfg) {
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
