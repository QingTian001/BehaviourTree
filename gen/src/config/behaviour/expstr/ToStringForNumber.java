package config.behaviour.expstr;

// ToStringForNumber
public final class ToStringForNumber implements config.behaviour.expstr.ExpStr {
    @Override
    public config.behaviour.Expstrenum type() {
        return config.behaviour.Expstrenum.TOSTRINGFORNUMBER;
    }

    private config.behaviour.expnumber.ExpNumber num;

    private ToStringForNumber() {
    }

    public ToStringForNumber(config.behaviour.expnumber.ExpNumber num) {
        this.num = num;
    }

    public static ToStringForNumber _create(configgen.genjava.ConfigInput input) {
        ToStringForNumber self = new ToStringForNumber();
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
        if (!(other instanceof ToStringForNumber))
            return false;
        ToStringForNumber o = (ToStringForNumber) other;
        return num.equals(o.num);
    }

    @Override
    public String toString() {
        return "ToStringForNumber(" + num + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        num._resolve(mgr);
    }

}
