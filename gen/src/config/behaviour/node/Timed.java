package config.behaviour.node;

public class Timed implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.TIMED;
    }

    private boolean validOnFinish;
    private config.behaviour.ExpNumber timeMs;
    private config.behaviour.Node node;

    private Timed() {
    }

    public Timed(boolean validOnFinish, config.behaviour.ExpNumber timeMs, config.behaviour.Node node) {
        this.validOnFinish = validOnFinish;
        this.timeMs = timeMs;
        this.node = node;
    }

    public static Timed _create(configgen.genjava.ConfigInput input) {
        Timed self = new Timed();
        self.validOnFinish = input.readBool();
        self.timeMs = config.behaviour.ExpNumber._create(input);
        self.node = config.behaviour.Node._create(input);
        return self;
    }

    public boolean getValidOnFinish() {
        return validOnFinish;
    }

    public config.behaviour.ExpNumber getTimeMs() {
        return timeMs;
    }

    public config.behaviour.Node getNode() {
        return node;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(validOnFinish, timeMs, node);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Timed))
            return false;
        Timed o = (Timed) other;
        return validOnFinish == o.validOnFinish && timeMs.equals(o.timeMs) && node.equals(o.node);
    }

    @Override
    public String toString() {
        return "Timed(" + validOnFinish + "," + timeMs + "," + node + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        timeMs._resolve(mgr);
        node._resolve(mgr);
    }

}
