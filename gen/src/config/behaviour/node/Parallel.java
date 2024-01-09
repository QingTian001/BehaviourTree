package config.behaviour.node;

public class Parallel implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.PARALLEL;
    }

    private boolean oneSuccAllSucc;
    private boolean oneFailAllFail;
    private java.util.List<config.behaviour.Node> nodes;

    private Parallel() {
    }

    public Parallel(boolean oneSuccAllSucc, boolean oneFailAllFail, java.util.List<config.behaviour.Node> nodes) {
        this.oneSuccAllSucc = oneSuccAllSucc;
        this.oneFailAllFail = oneFailAllFail;
        this.nodes = nodes;
    }

    public static Parallel _create(configgen.genjava.ConfigInput input) {
        Parallel self = new Parallel();
        self.oneSuccAllSucc = input.readBool();
        self.oneFailAllFail = input.readBool();
        self.nodes = new java.util.ArrayList<>();
        for (int c = input.readInt(); c > 0; c--) {
            self.nodes.add(config.behaviour.Node._create(input));
        }
        return self;
    }

    public boolean getOneSuccAllSucc() {
        return oneSuccAllSucc;
    }

    public boolean getOneFailAllFail() {
        return oneFailAllFail;
    }

    public java.util.List<config.behaviour.Node> getNodes() {
        return nodes;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(oneSuccAllSucc, oneFailAllFail, nodes);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Parallel))
            return false;
        Parallel o = (Parallel) other;
        return oneSuccAllSucc == o.oneSuccAllSucc && oneFailAllFail == o.oneFailAllFail && nodes.equals(o.nodes);
    }

    @Override
    public String toString() {
        return "Parallel(" + oneSuccAllSucc + "," + oneFailAllFail + "," + nodes + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        nodes.forEach( e -> {
            e._resolve(mgr);
        });
    }

}
