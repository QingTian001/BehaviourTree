package config.behaviour.node;

// CompParallel
public final class CompParallel implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.COMPPARALLEL;
    }

    private boolean oneSuccAllSucc;
    private boolean oneFailAllFail;
    private java.util.List<config.behaviour.node.Node> nodes;
    private java.util.List<config.behaviour.node.Node> enterActions;
    private java.util.List<config.behaviour.node.Node> cleanActions;
    private String note;

    private CompParallel() {
    }

    public CompParallel(boolean oneSuccAllSucc, boolean oneFailAllFail, java.util.List<config.behaviour.node.Node> nodes, java.util.List<config.behaviour.node.Node> enterActions, java.util.List<config.behaviour.node.Node> cleanActions, String note) {
        this.oneSuccAllSucc = oneSuccAllSucc;
        this.oneFailAllFail = oneFailAllFail;
        this.nodes = nodes;
        this.enterActions = enterActions;
        this.cleanActions = cleanActions;
        this.note = note;
    }

    public static CompParallel _create(configgen.genjava.ConfigInput input) {
        CompParallel self = new CompParallel();
        self.oneSuccAllSucc = input.readBool();
        self.oneFailAllFail = input.readBool();
        {
            int c = input.readInt();
            if (c == 0) {
                self.nodes = java.util.Collections.emptyList();
            } else {
                self.nodes = new java.util.ArrayList<>(c);
                for (; c > 0; c--) {
                    self.nodes.add(config.behaviour.node.Node._create(input));
                }
            }
        }
        {
            int c = input.readInt();
            if (c == 0) {
                self.enterActions = java.util.Collections.emptyList();
            } else {
                self.enterActions = new java.util.ArrayList<>(c);
                for (; c > 0; c--) {
                    self.enterActions.add(config.behaviour.node.Node._create(input));
                }
            }
        }
        {
            int c = input.readInt();
            if (c == 0) {
                self.cleanActions = java.util.Collections.emptyList();
            } else {
                self.cleanActions = new java.util.ArrayList<>(c);
                for (; c > 0; c--) {
                    self.cleanActions.add(config.behaviour.node.Node._create(input));
                }
            }
        }
        self.note = input.readStringInPool();
        return self;
    }

    public boolean getOneSuccAllSucc() {
        return oneSuccAllSucc;
    }

    public boolean getOneFailAllFail() {
        return oneFailAllFail;
    }

    public java.util.List<config.behaviour.node.Node> getNodes() {
        return nodes;
    }

    public java.util.List<config.behaviour.node.Node> getEnterActions() {
        return enterActions;
    }

    public java.util.List<config.behaviour.node.Node> getCleanActions() {
        return cleanActions;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(oneSuccAllSucc, oneFailAllFail, nodes, enterActions, cleanActions, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CompParallel))
            return false;
        CompParallel o = (CompParallel) other;
        return oneSuccAllSucc == o.oneSuccAllSucc && oneFailAllFail == o.oneFailAllFail && nodes.equals(o.nodes) && enterActions.equals(o.enterActions) && cleanActions.equals(o.cleanActions) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "CompParallel(" + oneSuccAllSucc + "," + oneFailAllFail + "," + nodes + "," + enterActions + "," + cleanActions + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        for (config.behaviour.node.Node e : nodes) {
            e._resolve(mgr);
        }
        for (config.behaviour.node.Node e : enterActions) {
            e._resolve(mgr);
        }
        for (config.behaviour.node.Node e : cleanActions) {
            e._resolve(mgr);
        }
    }

}
