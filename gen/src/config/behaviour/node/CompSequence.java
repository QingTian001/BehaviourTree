package config.behaviour.node;

// CompSequence
public final class CompSequence implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.COMPSEQUENCE;
    }

    private config.behaviour.condition.Condition precond;
    private java.util.List<config.behaviour.node.Node> nodes;
    private java.util.List<config.behaviour.node.Node> enterActions;
    private java.util.List<config.behaviour.node.Node> cleanActions;
    private String note;

    private CompSequence() {
    }

    public CompSequence(config.behaviour.condition.Condition precond, java.util.List<config.behaviour.node.Node> nodes, java.util.List<config.behaviour.node.Node> enterActions, java.util.List<config.behaviour.node.Node> cleanActions, String note) {
        this.precond = precond;
        this.nodes = nodes;
        this.enterActions = enterActions;
        this.cleanActions = cleanActions;
        this.note = note;
    }

    public static CompSequence _create(configgen.genjava.ConfigInput input) {
        CompSequence self = new CompSequence();
        self.precond = config.behaviour.condition.Condition._create(input);
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

    public config.behaviour.condition.Condition getPrecond() {
        return precond;
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
        return java.util.Objects.hash(precond, nodes, enterActions, cleanActions, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CompSequence))
            return false;
        CompSequence o = (CompSequence) other;
        return precond.equals(o.precond) && nodes.equals(o.nodes) && enterActions.equals(o.enterActions) && cleanActions.equals(o.cleanActions) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "CompSequence(" + precond + "," + nodes + "," + enterActions + "," + cleanActions + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        precond._resolve(mgr);
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
