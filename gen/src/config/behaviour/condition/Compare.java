package config.behaviour.condition;

// Compare
public final class Compare implements config.behaviour.condition.Condition {
    @Override
    public config.behaviour.Conditionenum type() {
        return config.behaviour.Conditionenum.COMPARE;
    }

    private config.behaviour.expnumber.ExpNumber left;
    private config.behaviour.expnumber.ExpNumber right;
    private int comarator;
    private config.behaviour.Compareenum RefComarator;

    private Compare() {
    }

    public Compare(config.behaviour.expnumber.ExpNumber left, config.behaviour.expnumber.ExpNumber right, int comarator) {
        this.left = left;
        this.right = right;
        this.comarator = comarator;
    }

    public static Compare _create(configgen.genjava.ConfigInput input) {
        Compare self = new Compare();
        self.left = config.behaviour.expnumber.ExpNumber._create(input);
        self.right = config.behaviour.expnumber.ExpNumber._create(input);
        self.comarator = input.readInt();
        return self;
    }

    public config.behaviour.expnumber.ExpNumber getLeft() {
        return left;
    }

    public config.behaviour.expnumber.ExpNumber getRight() {
        return right;
    }

    public int getComarator() {
        return comarator;
    }

    public config.behaviour.Compareenum refComarator() {
        return RefComarator;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(left, right, comarator);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Compare))
            return false;
        Compare o = (Compare) other;
        return left.equals(o.left) && right.equals(o.right) && comarator == o.comarator;
    }

    @Override
    public String toString() {
        return "Compare(" + left + "," + right + "," + comarator + ")";
    }

    public void _resolveDirect(config.ConfigMgr mgr) {
        RefComarator = config.behaviour.Compareenum.get(comarator);
        configgen.genjava.LoadValueErrs.requireNonNull(RefComarator, "Compare.comarator -> behaviour.compareenum", comarator);
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        left._resolve(mgr);
        right._resolve(mgr);
        _resolveDirect(mgr);
    }

}
