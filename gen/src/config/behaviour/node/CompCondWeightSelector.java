package config.behaviour.node;

// CompWeightSelector
public final class CompCondWeightSelector implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.COMPCONDWEIGHTSELECTOR;
    }

    private config.behaviour.condition.Condition precond;
    private java.util.List<config.behaviour.CondWeightNode> weightNodes;
    private java.util.List<config.behaviour.node.Node> enterActions;
    private java.util.List<config.behaviour.node.Node> cleanActions;
    private String note;

    private CompCondWeightSelector() {
    }

    public CompCondWeightSelector(config.behaviour.condition.Condition precond, java.util.List<config.behaviour.CondWeightNode> weightNodes, java.util.List<config.behaviour.node.Node> enterActions, java.util.List<config.behaviour.node.Node> cleanActions, String note) {
        this.precond = precond;
        this.weightNodes = weightNodes;
        this.enterActions = enterActions;
        this.cleanActions = cleanActions;
        this.note = note;
    }

    public static CompCondWeightSelector _create(configgen.genjava.ConfigInput input) {
        CompCondWeightSelector self = new CompCondWeightSelector();
        self.precond = config.behaviour.condition.Condition._create(input);
        {
            int c = input.readInt();
            if (c == 0) {
                self.weightNodes = java.util.Collections.emptyList();
            } else {
                self.weightNodes = new java.util.ArrayList<>(c);
                for (; c > 0; c--) {
                    self.weightNodes.add(config.behaviour.CondWeightNode._create(input));
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

    public java.util.List<config.behaviour.CondWeightNode> getWeightNodes() {
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
        if (!(other instanceof CompCondWeightSelector))
            return false;
        CompCondWeightSelector o = (CompCondWeightSelector) other;
        return precond.equals(o.precond) && weightNodes.equals(o.weightNodes) && enterActions.equals(o.enterActions) && cleanActions.equals(o.cleanActions) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "CompCondWeightSelector(" + precond + "," + weightNodes + "," + enterActions + "," + cleanActions + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        precond._resolve(mgr);
        for (config.behaviour.CondWeightNode e : weightNodes) {
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
