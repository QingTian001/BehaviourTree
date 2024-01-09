package config.behaviour.condition;

public class And implements config.behaviour.Condition {
    @Override
    public config.behaviour.Conditionenum type() {
        return config.behaviour.Conditionenum.AND;
    }

    private config.behaviour.Condition cond1;
    private config.behaviour.Condition cond2;

    private And() {
    }

    public And(config.behaviour.Condition cond1, config.behaviour.Condition cond2) {
        this.cond1 = cond1;
        this.cond2 = cond2;
    }

    public static And _create(configgen.genjava.ConfigInput input) {
        And self = new And();
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
        if (!(other instanceof And))
            return false;
        And o = (And) other;
        return cond1.equals(o.cond1) && cond2.equals(o.cond2);
    }

    @Override
    public String toString() {
        return "And(" + cond1 + "," + cond2 + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        cond1._resolve(mgr);
        cond2._resolve(mgr);
    }

}
