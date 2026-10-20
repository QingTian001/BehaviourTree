package config.behaviour.condition;

// Not
public final class Not implements config.behaviour.condition.Condition {
    @Override
    public config.behaviour.Conditionenum type() {
        return config.behaviour.Conditionenum.NOT;
    }

    private config.behaviour.condition.Condition cond;

    private Not() {
    }

    public Not(config.behaviour.condition.Condition cond) {
        this.cond = cond;
    }

    public static Not _create(configgen.genjava.ConfigInput input) {
        Not self = new Not();
        self.cond = config.behaviour.condition.Condition._create(input);
        return self;
    }

    public config.behaviour.condition.Condition getCond() {
        return cond;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(cond);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Not))
            return false;
        Not o = (Not) other;
        return cond.equals(o.cond);
    }

    @Override
    public String toString() {
        return "Not(" + cond + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        cond._resolve(mgr);
    }

}
