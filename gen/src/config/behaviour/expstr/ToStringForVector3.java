package config.behaviour.expstr;

// ToStringForVector3
public final class ToStringForVector3 implements config.behaviour.expstr.ExpStr {
    @Override
    public config.behaviour.Expstrenum type() {
        return config.behaviour.Expstrenum.TOSTRINGFORVECTOR3;
    }

    private config.behaviour.expvector3.ExpVector3 v3;

    private ToStringForVector3() {
    }

    public ToStringForVector3(config.behaviour.expvector3.ExpVector3 v3) {
        this.v3 = v3;
    }

    public static ToStringForVector3 _create(configgen.genjava.ConfigInput input) {
        ToStringForVector3 self = new ToStringForVector3();
        self.v3 = config.behaviour.expvector3.ExpVector3._create(input);
        return self;
    }

    public config.behaviour.expvector3.ExpVector3 getV3() {
        return v3;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(v3);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ToStringForVector3))
            return false;
        ToStringForVector3 o = (ToStringForVector3) other;
        return v3.equals(o.v3);
    }

    @Override
    public String toString() {
        return "ToStringForVector3(" + v3 + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        v3._resolve(mgr);
    }

}
