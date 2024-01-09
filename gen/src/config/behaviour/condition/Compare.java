package config.behaviour.condition;

public class Compare implements config.behaviour.Condition {
    @Override
    public config.behaviour.Conditionenum type() {
        return config.behaviour.Conditionenum.COMPARE;
    }

    private config.behaviour.ExpNumber left;
    private config.behaviour.ExpNumber right;
    private int comarator;
    private config.behaviour.Compareenum RefComarator;

    private Compare() {
    }

    public Compare(config.behaviour.ExpNumber left, config.behaviour.ExpNumber right, int comarator) {
        this.left = left;
        this.right = right;
        this.comarator = comarator;
    }

    public static Compare _create(configgen.genjava.ConfigInput input) {
        Compare self = new Compare();
        self.left = config.behaviour.ExpNumber._create(input);
        self.right = config.behaviour.ExpNumber._create(input);
        self.comarator = input.readInt();
        return self;
    }

    public config.behaviour.ExpNumber getLeft() {
        return left;
    }

    public config.behaviour.ExpNumber getRight() {
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

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        left._resolve(mgr);
        right._resolve(mgr);
        RefComarator = config.behaviour.Compareenum.get(comarator);
        java.util.Objects.requireNonNull(RefComarator);
    }

}
