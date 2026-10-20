package behaviour.node.composite;

/**
 * CompParallel - static definition only.
 */
public class CompParallel extends Composite<config.behaviour.node.CompParallel> {

    public CompParallel(config.behaviour.node.CompParallel atomCfg) {
        super(atomCfg);
    }

    @Override
    protected java.util.List<? extends config.behaviour.node.Node> getConfigNodeList() {
        return getAtomCfg().getNodes();
    }

    @Override
    protected java.util.List<? extends config.behaviour.node.Node> getConfigCleanActionList() {
        return getAtomCfg().getCleanActions();
    }

    @Override
    protected java.util.List<? extends config.behaviour.node.Node> getConfigEnterActionList() {
        return getAtomCfg().getEnterActions();
    }
}
