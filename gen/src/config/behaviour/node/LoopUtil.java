package config.behaviour.node;

public class LoopUtil implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.LOOPUTIL;
    }

    private boolean validOnFinish;
    private config.behaviour.Condition cond;
    private config.behaviour.Node node;

    private LoopUtil() {
    }

    public LoopUtil(boolean validOnFinish, config.behaviour.Condition cond, config.behaviour.Node node) {
        this.validOnFinish = validOnFinish;
        this.cond = cond;
        this.node = node;
    }

    public static LoopUtil _create(configgen.genjava.ConfigInput input) {
        LoopUtil self = new LoopUtil();
        self.validOnFinish = input.readBool();
        self.cond = config.behaviour.Condition._create(input);
        self.node = config.behaviour.Node._create(input);
        return self;
    }

    public boolean getValidOnFinish() {
        return validOnFinish;
    }

    public config.behaviour.Condition getCond() {
        return cond;
    }

    public config.behaviour.Node getNode() {
        return node;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(validOnFinish, cond, node);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof LoopUtil))
            return false;
        LoopUtil o = (LoopUtil) other;
        return validOnFinish == o.validOnFinish && cond.equals(o.cond) && node.equals(o.node);
    }

    @Override
    public String toString() {
        return "LoopUtil(" + validOnFinish + "," + cond + "," + node + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        cond._resolve(mgr);
        node._resolve(mgr);
    }

}
