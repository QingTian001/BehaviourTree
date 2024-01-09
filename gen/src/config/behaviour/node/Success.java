package config.behaviour.node;

public class Success implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.SUCCESS;
    }

    private boolean validOnFinish;
    private config.behaviour.Node node;

    private Success() {
    }

    public Success(boolean validOnFinish, config.behaviour.Node node) {
        this.validOnFinish = validOnFinish;
        this.node = node;
    }

    public static Success _create(configgen.genjava.ConfigInput input) {
        Success self = new Success();
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
        if (!(other instanceof Success))
            return false;
        Success o = (Success) other;
        return validOnFinish == o.validOnFinish && node.equals(o.node);
    }

    @Override
    public String toString() {
        return "Success(" + validOnFinish + "," + node + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        node._resolve(mgr);
    }

}
