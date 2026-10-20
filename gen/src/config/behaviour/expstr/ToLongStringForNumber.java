package config.behaviour.expstr;

// ToLongStringForNumber
public final class ToLongStringForNumber implements config.behaviour.expstr.ExpStr {
    @Override
    public config.behaviour.Expstrenum type() {
        return config.behaviour.Expstrenum.TOLONGSTRINGFORNUMBER;
    }

    private config.behaviour.expnumber.ExpNumber num;

    private ToLongStringForNumber() {
    }

    public ToLongStringForNumber(config.behaviour.expnumber.ExpNumber num) {
        this.num = num;
    }

    public static ToLongStringForNumber _create(configgen.genjava.ConfigInput input) {
        ToLongStringForNumber self = new ToLongStringForNumber();
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
        if (!(other instanceof ToLongStringForNumber))
            return false;
        ToLongStringForNumber o = (ToLongStringForNumber) other;
        return num.equals(o.num);
    }

    @Override
    public String toString() {
        return "ToLongStringForNumber(" + num + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        num._resolve(mgr);
    }

}
