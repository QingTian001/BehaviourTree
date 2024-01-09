package config.behaviour.node;

public class Selector implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.SELECTOR;
    }

    private config.behaviour.Condition precond;
    private java.util.List<config.behaviour.Node> nodes;

    private Selector() {
    }

    public Selector(config.behaviour.Condition precond, java.util.List<config.behaviour.Node> nodes) {
        this.precond = precond;
        this.nodes = nodes;
    }

    public static Selector _create(configgen.genjava.ConfigInput input) {
        Selector self = new Selector();
        self.precond = config.behaviour.Condition._create(input);
        self.nodes = new java.util.ArrayList<>();
        for (int c = input.readInt(); c > 0; c--) {
            self.nodes.add(config.behaviour.Node._create(input));
        }
        return self;
    }

    public config.behaviour.Condition getPrecond() {
        return precond;
    }

    public java.util.List<config.behaviour.Node> getNodes() {
        return nodes;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(precond, nodes);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Selector))
            return false;
        Selector o = (Selector) other;
        return precond.equals(o.precond) && nodes.equals(o.nodes);
    }

    @Override
    public String toString() {
        return "Selector(" + precond + "," + nodes + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        precond._resolve(mgr);
        nodes.forEach( e -> {
            e._resolve(mgr);
        });
    }

}
