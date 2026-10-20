package config.behaviour.condition;

// StrEqual
public final class StrEqual implements config.behaviour.condition.Condition {
    @Override
    public config.behaviour.Conditionenum type() {
        return config.behaviour.Conditionenum.STREQUAL;
    }

    private config.behaviour.expstr.ExpStr left;
    private config.behaviour.expstr.ExpStr right;

    private StrEqual() {
    }

    public StrEqual(config.behaviour.expstr.ExpStr left, config.behaviour.expstr.ExpStr right) {
        this.left = left;
        this.right = right;
    }

    public static StrEqual _create(configgen.genjava.ConfigInput input) {
        StrEqual self = new StrEqual();
        self.left = config.behaviour.expstr.ExpStr._create(input);
        self.right = config.behaviour.expstr.ExpStr._create(input);
        return self;
    }

    public config.behaviour.expstr.ExpStr getLeft() {
        return left;
    }

    public config.behaviour.expstr.ExpStr getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(left, right);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StrEqual))
            return false;
        StrEqual o = (StrEqual) other;
        return left.equals(o.left) && right.equals(o.right);
    }

    @Override
    public String toString() {
        return "StrEqual(" + left + "," + right + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        left._resolve(mgr);
        right._resolve(mgr);
    }

}
