package config.behaviour.expnumber;

// Calculate
public final class Calculate implements config.behaviour.expnumber.ExpNumber {
    @Override
    public config.behaviour.Expnumberenum type() {
        return config.behaviour.Expnumberenum.CALCULATE;
    }

    private config.behaviour.expnumber.ExpNumber left;
    private config.behaviour.expnumber.ExpNumber right;
    private int calculator;
    private config.behaviour.Calculatorenum RefCalculator;

    private Calculate() {
    }

    public Calculate(config.behaviour.expnumber.ExpNumber left, config.behaviour.expnumber.ExpNumber right, int calculator) {
        this.left = left;
        this.right = right;
        this.calculator = calculator;
    }

    public static Calculate _create(configgen.genjava.ConfigInput input) {
        Calculate self = new Calculate();
        self.left = config.behaviour.expnumber.ExpNumber._create(input);
        self.right = config.behaviour.expnumber.ExpNumber._create(input);
        self.calculator = input.readInt();
        return self;
    }

    public config.behaviour.expnumber.ExpNumber getLeft() {
        return left;
    }

    public config.behaviour.expnumber.ExpNumber getRight() {
        return right;
    }

    public int getCalculator() {
        return calculator;
    }

    public config.behaviour.Calculatorenum refCalculator() {
        return RefCalculator;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(left, right, calculator);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Calculate))
            return false;
        Calculate o = (Calculate) other;
        return left.equals(o.left) && right.equals(o.right) && calculator == o.calculator;
    }

    @Override
    public String toString() {
        return "Calculate(" + left + "," + right + "," + calculator + ")";
    }

    public void _resolveDirect(config.ConfigMgr mgr) {
        RefCalculator = config.behaviour.Calculatorenum.get(calculator);
        configgen.genjava.LoadValueErrs.requireNonNull(RefCalculator, "Calculate.calculator -> behaviour.calculatorenum", calculator);
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        left._resolve(mgr);
        right._resolve(mgr);
        _resolveDirect(mgr);
    }

}
