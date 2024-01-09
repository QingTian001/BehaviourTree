package config.behaviour.node;

public class Loop implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.LOOP;
    }

    private boolean validOnFinish;
    private config.behaviour.Node node;
    private int loopNum;

    private Loop() {
    }

    public Loop(boolean validOnFinish, config.behaviour.Node node, int loopNum) {
        this.validOnFinish = validOnFinish;
        this.node = node;
        this.loopNum = loopNum;
    }

    public static Loop _create(configgen.genjava.ConfigInput input) {
        Loop self = new Loop();
        self.validOnFinish = input.readBool();
        self.node = config.behaviour.Node._create(input);
        self.loopNum = input.readInt();
        return self;
    }

    public boolean getValidOnFinish() {
        return validOnFinish;
    }

    public config.behaviour.Node getNode() {
        return node;
    }

    public int getLoopNum() {
        return loopNum;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(validOnFinish, node, loopNum);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Loop))
            return false;
        Loop o = (Loop) other;
        return validOnFinish == o.validOnFinish && node.equals(o.node) && loopNum == o.loopNum;
    }

    @Override
    public String toString() {
        return "Loop(" + validOnFinish + "," + node + "," + loopNum + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        node._resolve(mgr);
    }

}
