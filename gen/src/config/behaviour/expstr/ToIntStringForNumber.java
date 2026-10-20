package config.behaviour.expstr;

// ToIntStringForNumber
public final class ToIntStringForNumber implements config.behaviour.expstr.ExpStr {
    @Override
    public config.behaviour.Expstrenum type() {
        return config.behaviour.Expstrenum.TOINTSTRINGFORNUMBER;
    }

    private config.behaviour.expnumber.ExpNumber num;

    private ToIntStringForNumber() {
    }

    public ToIntStringForNumber(config.behaviour.expnumber.ExpNumber num) {
        this.num = num;
    }

    public static ToIntStringForNumber _create(configgen.genjava.ConfigInput input) {
        ToIntStringForNumber self = new ToIntStringForNumber();
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
        if (!(other instanceof ToIntStringForNumber))
            return false;
        ToIntStringForNumber o = (ToIntStringForNumber) other;
        return num.equals(o.num);
    }

    @Override
    public String toString() {
        return "ToIntStringForNumber(" + num + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        num._resolve(mgr);
    }

}
