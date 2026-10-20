package config.behaviour.node;

// CompIntSwitchCase
public final class CompIntSwitchCase implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.COMPINTSWITCHCASE;
    }

    private config.behaviour.condition.Condition precond;
    private config.behaviour.expnumber.ExpNumber caseValue;
    private config.behaviour.node.Node defaultCase;
    private java.util.List<config.behaviour.IntSwitchNode> cases;
    private java.util.List<config.behaviour.node.Node> enterActions;
    private java.util.List<config.behaviour.node.Node> cleanActions;
    private String note;

    private CompIntSwitchCase() {
    }

    public CompIntSwitchCase(config.behaviour.condition.Condition precond, config.behaviour.expnumber.ExpNumber caseValue, config.behaviour.node.Node defaultCase, java.util.List<config.behaviour.IntSwitchNode> cases, java.util.List<config.behaviour.node.Node> enterActions, java.util.List<config.behaviour.node.Node> cleanActions, String note) {
        this.precond = precond;
        this.caseValue = caseValue;
        this.defaultCase = defaultCase;
        this.cases = cases;
        this.enterActions = enterActions;
        this.cleanActions = cleanActions;
        this.note = note;
    }

    public static CompIntSwitchCase _create(configgen.genjava.ConfigInput input) {
        CompIntSwitchCase self = new CompIntSwitchCase();
        self.precond = config.behaviour.condition.Condition._create(input);
        self.caseValue = config.behaviour.expnumber.ExpNumber._create(input);
        self.defaultCase = config.behaviour.node.Node._create(input);
        {
            int c = input.readInt();
            if (c == 0) {
                self.cases = java.util.Collections.emptyList();
            } else {
                self.cases = new java.util.ArrayList<>(c);
                for (; c > 0; c--) {
                    self.cases.add(config.behaviour.IntSwitchNode._create(input));
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

    public config.behaviour.expnumber.ExpNumber getCaseValue() {
        return caseValue;
    }

    public config.behaviour.node.Node getDefaultCase() {
        return defaultCase;
    }

    public java.util.List<config.behaviour.IntSwitchNode> getCases() {
        return cases;
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
        return java.util.Objects.hash(precond, caseValue, defaultCase, cases, enterActions, cleanActions, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CompIntSwitchCase))
            return false;
        CompIntSwitchCase o = (CompIntSwitchCase) other;
        return precond.equals(o.precond) && caseValue.equals(o.caseValue) && defaultCase.equals(o.defaultCase) && cases.equals(o.cases) && enterActions.equals(o.enterActions) && cleanActions.equals(o.cleanActions) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "CompIntSwitchCase(" + precond + "," + caseValue + "," + defaultCase + "," + cases + "," + enterActions + "," + cleanActions + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        precond._resolve(mgr);
        caseValue._resolve(mgr);
        defaultCase._resolve(mgr);
        for (config.behaviour.IntSwitchNode e : cases) {
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
