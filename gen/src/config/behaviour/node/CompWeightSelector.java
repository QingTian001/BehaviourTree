package config.behaviour.node;

// CompWeightSelector
public final class CompWeightSelector implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.COMPWEIGHTSELECTOR;
    }

    private config.behaviour.condition.Condition precond;
    private java.util.List<config.behaviour.WeightNode> weightNodes;
    private java.util.List<config.behaviour.node.Node> enterActions;
    private java.util.List<config.behaviour.node.Node> cleanActions;
    private String note;

    private CompWeightSelector() {
    }

    public CompWeightSelector(config.behaviour.condition.Condition precond, java.util.List<config.behaviour.WeightNode> weightNodes, java.util.List<config.behaviour.node.Node> enterActions, java.util.List<config.behaviour.node.Node> cleanActions, String note) {
        this.precond = precond;
        this.weightNodes = weightNodes;
        this.enterActions = enterActions;
        this.cleanActions = cleanActions;
        this.note = note;
    }

    public static CompWeightSelector _create(configgen.genjava.ConfigInput input) {
        CompWeightSelector self = new CompWeightSelector();
        self.precond = config.behaviour.condition.Condition._create(input);
        {
            int c = input.readInt();
            if (c == 0) {
                self.weightNodes = java.util.Collections.emptyList();
            } else {
                self.weightNodes = new java.util.ArrayList<>(c);
                for (; c > 0; c--) {
                    self.weightNodes.add(config.behaviour.WeightNode._create(input));
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

    public java.util.List<config.behaviour.WeightNode> getWeightNodes() {
        return weightNodes;
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
        return java.util.Objects.hash(precond, weightNodes, enterActions, cleanActions, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CompWeightSelector))
            return false;
        CompWeightSelector o = (CompWeightSelector) other;
        return precond.equals(o.precond) && weightNodes.equals(o.weightNodes) && enterActions.equals(o.enterActions) && cleanActions.equals(o.cleanActions) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "CompWeightSelector(" + precond + "," + weightNodes + "," + enterActions + "," + cleanActions + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        precond._resolve(mgr);
        for (config.behaviour.WeightNode e : weightNodes) {
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
