package config.behaviour.expstr;

// ToFloatStringForNumber
public final class ToFloatStringForNumber implements config.behaviour.expstr.ExpStr {
    @Override
    public config.behaviour.Expstrenum type() {
        return config.behaviour.Expstrenum.TOFLOATSTRINGFORNUMBER;
    }

    private config.behaviour.expnumber.ExpNumber num;

    private ToFloatStringForNumber() {
    }

    public ToFloatStringForNumber(config.behaviour.expnumber.ExpNumber num) {
        this.num = num;
    }

    public static ToFloatStringForNumber _create(configgen.genjava.ConfigInput input) {
        ToFloatStringForNumber self = new ToFloatStringForNumber();
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
        if (!(other instanceof ToFloatStringForNumber))
            return false;
        ToFloatStringForNumber o = (ToFloatStringForNumber) other;
        return num.equals(o.num);
    }

    @Override
    public String toString() {
        return "ToFloatStringForNumber(" + num + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        num._resolve(mgr);
    }

}
