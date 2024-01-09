package config.behaviour.node;

public class Failure implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.FAILURE;
    }

    private boolean validOnFinish;
    private config.behaviour.Node node;

    private Failure() {
    }

    public Failure(boolean validOnFinish, config.behaviour.Node node) {
        this.validOnFinish = validOnFinish;
        this.node = node;
    }

    public static Failure _create(configgen.genjava.ConfigInput input) {
        Failure self = new Failure();
        self.validOnFinish = input.readBool();
        self.node = config.behaviour.Node._create(input);
        return self;
    }

    public boolean getValidOnFinish() {
        return validOnFinish;
    }

    public config.behaviour.Node getNode() {
        return node;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(validOnFinish, node);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Failure))
            return false;
        Failure o = (Failure) other;
        return validOnFinish == o.validOnFinish && node.equals(o.node);
    }

    @Override
    public String toString() {
        return "Failure(" + validOnFinish + "," + node + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        node._resolve(mgr);
    }

}
