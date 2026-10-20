package config.behaviour.node;

// DecSuccess
public final class DecSuccess implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.DECSUCCESS;
    }

    private boolean validOnFinish;
    private config.behaviour.node.Node node;
    private String note;

    private DecSuccess() {
    }

    public DecSuccess(boolean validOnFinish, config.behaviour.node.Node node, String note) {
        this.validOnFinish = validOnFinish;
        this.node = node;
        this.note = note;
    }

    public static DecSuccess _create(configgen.genjava.ConfigInput input) {
        DecSuccess self = new DecSuccess();
        self.validOnFinish = input.readBool();
        self.node = config.behaviour.node.Node._create(input);
        self.note = input.readStringInPool();
        return self;
    }

    public boolean getValidOnFinish() {
        return validOnFinish;
    }

    public config.behaviour.node.Node getNode() {
        return node;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(validOnFinish, node, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DecSuccess))
            return false;
        DecSuccess o = (DecSuccess) other;
        return validOnFinish == o.validOnFinish && node.equals(o.node) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "DecSuccess(" + validOnFinish + "," + node + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        node._resolve(mgr);
    }

}
