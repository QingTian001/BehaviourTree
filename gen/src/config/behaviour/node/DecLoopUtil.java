package config.behaviour.node;

// DecLoopUtil
public final class DecLoopUtil implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.DECLOOPUTIL;
    }

    private boolean validOnFinish;
    private config.behaviour.condition.Condition cond;
    private config.behaviour.node.Node node;
    private String note;

    private DecLoopUtil() {
    }

    public DecLoopUtil(boolean validOnFinish, config.behaviour.condition.Condition cond, config.behaviour.node.Node node, String note) {
        this.validOnFinish = validOnFinish;
        this.cond = cond;
        this.node = node;
        this.note = note;
    }

    public static DecLoopUtil _create(configgen.genjava.ConfigInput input) {
        DecLoopUtil self = new DecLoopUtil();
        self.validOnFinish = input.readBool();
        self.cond = config.behaviour.condition.Condition._create(input);
        self.node = config.behaviour.node.Node._create(input);
        self.note = input.readStringInPool();
        return self;
    }

    public boolean getValidOnFinish() {
        return validOnFinish;
    }

    public config.behaviour.condition.Condition getCond() {
        return cond;
    }

    public config.behaviour.node.Node getNode() {
        return node;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(validOnFinish, cond, node, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DecLoopUtil))
            return false;
        DecLoopUtil o = (DecLoopUtil) other;
        return validOnFinish == o.validOnFinish && cond.equals(o.cond) && node.equals(o.node) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "DecLoopUtil(" + validOnFinish + "," + cond + "," + node + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        cond._resolve(mgr);
        node._resolve(mgr);
    }

}
