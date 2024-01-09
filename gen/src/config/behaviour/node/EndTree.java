package config.behaviour.node;

public class EndTree implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ENDTREE;
    }

    private int status;
    private config.behaviour.Statusenum RefStatus;

    private EndTree() {
    }

    public EndTree(int status) {
        this.status = status;
    }

    public static EndTree _create(configgen.genjava.ConfigInput input) {
        EndTree self = new EndTree();
        self.status = input.readInt();
        return self;
    }

    public int getStatus() {
        return status;
    }

    public config.behaviour.Statusenum refStatus() {
        return RefStatus;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(status);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof EndTree))
            return false;
        EndTree o = (EndTree) other;
        return status == o.status;
    }

    @Override
    public String toString() {
        return "EndTree(" + status + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        RefStatus = config.behaviour.Statusenum.get(status);
        java.util.Objects.requireNonNull(RefStatus);
    }

}
