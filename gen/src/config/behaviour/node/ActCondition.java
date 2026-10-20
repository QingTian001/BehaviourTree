package config.behaviour.node;

// ActCondition
public final class ActCondition implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTCONDITION;
    }

    private config.behaviour.condition.Condition cond;
    private String note;

    private ActCondition() {
    }

    public ActCondition(config.behaviour.condition.Condition cond, String note) {
        this.cond = cond;
        this.note = note;
    }

    public static ActCondition _create(configgen.genjava.ConfigInput input) {
        ActCondition self = new ActCondition();
        self.cond = config.behaviour.condition.Condition._create(input);
        self.note = input.readStringInPool();
        return self;
    }

    public config.behaviour.condition.Condition getCond() {
        return cond;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(cond, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActCondition))
            return false;
        ActCondition o = (ActCondition) other;
        return cond.equals(o.cond) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "ActCondition(" + cond + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        cond._resolve(mgr);
    }

}
