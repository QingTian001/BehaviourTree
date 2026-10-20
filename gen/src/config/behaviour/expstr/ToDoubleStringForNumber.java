package config.behaviour.expstr;

// ToDoubleStringForNumber
public final class ToDoubleStringForNumber implements config.behaviour.expstr.ExpStr {
    @Override
    public config.behaviour.Expstrenum type() {
        return config.behaviour.Expstrenum.TODOUBLESTRINGFORNUMBER;
    }

    private config.behaviour.expnumber.ExpNumber num;

    private ToDoubleStringForNumber() {
    }

    public ToDoubleStringForNumber(config.behaviour.expnumber.ExpNumber num) {
        this.num = num;
    }

    public static ToDoubleStringForNumber _create(configgen.genjava.ConfigInput input) {
        ToDoubleStringForNumber self = new ToDoubleStringForNumber();
        self.num = config.behaviour.expnumber.ExpNumber._create(input);
        return self;
    }

    public config.behaviour.expnumber.ExpNumber getNum() {
        return num;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(num);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ToDoubleStringForNumber))
            return false;
        ToDoubleStringForNumber o = (ToDoubleStringForNumber) other;
        return num.equals(o.num);
    }

    @Override
    public String toString() {
        return "ToDoubleStringForNumber(" + num + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        num._resolve(mgr);
    }

}
