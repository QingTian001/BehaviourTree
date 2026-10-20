package config.behaviour.expnumber;

// Min
public final class Min implements config.behaviour.expnumber.ExpNumber {
    @Override
    public config.behaviour.Expnumberenum type() {
        return config.behaviour.Expnumberenum.MIN;
    }

    private config.behaviour.expnumber.ExpNumber left;
    private config.behaviour.expnumber.ExpNumber right;

    private Min() {
    }

    public Min(config.behaviour.expnumber.ExpNumber left, config.behaviour.expnumber.ExpNumber right) {
        this.left = left;
        this.right = right;
    }

    public static Min _create(configgen.genjava.ConfigInput input) {
        Min self = new Min();
        self.left = config.behaviour.expnumber.ExpNumber._create(input);
        self.right = config.behaviour.expnumber.ExpNumber._create(input);
        return self;
    }

    public config.behaviour.expnumber.ExpNumber getLeft() {
        return left;
    }

    public config.behaviour.expnumber.ExpNumber getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(left, right);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Min))
            return false;
        Min o = (Min) other;
        return left.equals(o.left) && right.equals(o.right);
    }

    @Override
    public String toString() {
        return "Min(" + left + "," + right + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        left._resolve(mgr);
        right._resolve(mgr);
    }

}
