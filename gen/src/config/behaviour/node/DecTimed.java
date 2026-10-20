package config.behaviour.node;

// DecTimed
public final class DecTimed implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.DECTIMED;
    }

    private boolean validOnFinish;
    private config.behaviour.expnumber.ExpNumber timeMs;
    private config.behaviour.node.Node node;
    private String note;

    private DecTimed() {
    }

    public DecTimed(boolean validOnFinish, config.behaviour.expnumber.ExpNumber timeMs, config.behaviour.node.Node node, String note) {
        this.validOnFinish = validOnFinish;
        this.timeMs = timeMs;
        this.node = node;
        this.note = note;
    }

    public static DecTimed _create(configgen.genjava.ConfigInput input) {
        DecTimed self = new DecTimed();
        self.validOnFinish = input.readBool();
        self.timeMs = config.behaviour.expnumber.ExpNumber._create(input);
        self.node = config.behaviour.node.Node._create(input);
        self.note = input.readStringInPool();
        return self;
    }

    public boolean getValidOnFinish() {
        return validOnFinish;
    }

    public config.behaviour.expnumber.ExpNumber getTimeMs() {
        return timeMs;
    }

    public config.behaviour.node.Node getNode() {
        return node;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(validOnFinish, timeMs, node, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DecTimed))
            return false;
        DecTimed o = (DecTimed) other;
        return validOnFinish == o.validOnFinish && timeMs.equals(o.timeMs) && node.equals(o.node) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "DecTimed(" + validOnFinish + "," + timeMs + "," + node + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        timeMs._resolve(mgr);
        node._resolve(mgr);
    }

}
