package config.behaviour.condition;

public class Or implements config.behaviour.Condition {
    @Override
    public config.behaviour.Conditionenum type() {
        return config.behaviour.Conditionenum.OR;
    }

    private config.behaviour.Condition cond1;
    private config.behaviour.Condition cond2;

    private Or() {
    }

    public Or(config.behaviour.Condition cond1, config.behaviour.Condition cond2) {
        this.cond1 = cond1;
        this.cond2 = cond2;
    }

    public static Or _create(configgen.genjava.ConfigInput input) {
        Or self = new Or();
        self.cond1 = config.behaviour.Condition._create(input);
        self.cond2 = config.behaviour.Condition._create(input);
        return self;
    }

    public config.behaviour.Condition getCond1() {
        return cond1;
    }

    public config.behaviour.Condition getCond2() {
        return cond2;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(cond1, cond2);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Or))
            return false;
        Or o = (Or) other;
        return cond1.equals(o.cond1) && cond2.equals(o.cond2);
    }

    @Override
    public String toString() {
        return "Or(" + cond1 + "," + cond2 + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        cond1._resolve(mgr);
        cond2._resolve(mgr);
    }

}
