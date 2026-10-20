package config.behaviour.node;

// DecLoop
public final class DecLoop implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.DECLOOP;
    }

    private boolean validOnFinish;
    private config.behaviour.node.Node node;
    private int loopNum;
    private String note;

    private DecLoop() {
    }

    public DecLoop(boolean validOnFinish, config.behaviour.node.Node node, int loopNum, String note) {
        this.validOnFinish = validOnFinish;
        this.node = node;
        this.loopNum = loopNum;
        this.note = note;
    }

    public static DecLoop _create(configgen.genjava.ConfigInput input) {
        DecLoop self = new DecLoop();
        self.validOnFinish = input.readBool();
        self.node = config.behaviour.node.Node._create(input);
        self.loopNum = input.readInt();
        self.note = input.readStringInPool();
        return self;
    }

    public boolean getValidOnFinish() {
        return validOnFinish;
    }

    public config.behaviour.node.Node getNode() {
        return node;
    }

    public int getLoopNum() {
        return loopNum;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(validOnFinish, node, loopNum, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DecLoop))
            return false;
        DecLoop o = (DecLoop) other;
        return validOnFinish == o.validOnFinish && node.equals(o.node) && loopNum == o.loopNum && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "DecLoop(" + validOnFinish + "," + node + "," + loopNum + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        node._resolve(mgr);
    }

}
