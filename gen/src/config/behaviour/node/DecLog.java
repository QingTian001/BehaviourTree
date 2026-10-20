package config.behaviour.node;

// DecLog
public final class DecLog implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.DECLOG;
    }

    private boolean validOnFinish;
    private config.behaviour.node.Node node;
    private String msg;

    private DecLog() {
    }

    public DecLog(boolean validOnFinish, config.behaviour.node.Node node, String msg) {
        this.validOnFinish = validOnFinish;
        this.node = node;
        this.msg = msg;
    }

    public static DecLog _create(configgen.genjava.ConfigInput input) {
        DecLog self = new DecLog();
        self.validOnFinish = input.readBool();
        self.node = config.behaviour.node.Node._create(input);
        self.msg = input.readStringInPool();
        return self;
    }

    public boolean getValidOnFinish() {
        return validOnFinish;
    }

    public config.behaviour.node.Node getNode() {
        return node;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(validOnFinish, node, msg);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DecLog))
            return false;
        DecLog o = (DecLog) other;
        return validOnFinish == o.validOnFinish && node.equals(o.node) && msg.equals(o.msg);
    }

    @Override
    public String toString() {
        return "DecLog(" + validOnFinish + "," + node + "," + msg + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        node._resolve(mgr);
    }

}
