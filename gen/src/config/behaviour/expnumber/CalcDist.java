package config.behaviour.expnumber;

// CalcDist
public final class CalcDist implements config.behaviour.expnumber.ExpNumber {
    @Override
    public config.behaviour.Expnumberenum type() {
        return config.behaviour.Expnumberenum.CALCDIST;
    }

    private config.behaviour.expvector3.ExpVector3 left;
    private config.behaviour.expvector3.ExpVector3 right;

    private CalcDist() {
    }

    public CalcDist(config.behaviour.expvector3.ExpVector3 left, config.behaviour.expvector3.ExpVector3 right) {
        this.left = left;
        this.right = right;
    }

    public static CalcDist _create(configgen.genjava.ConfigInput input) {
        CalcDist self = new CalcDist();
        self.left = config.behaviour.expvector3.ExpVector3._create(input);
        self.right = config.behaviour.expvector3.ExpVector3._create(input);
        return self;
    }

    public config.behaviour.expvector3.ExpVector3 getLeft() {
        return left;
    }

    public config.behaviour.expvector3.ExpVector3 getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(left, right);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CalcDist))
            return false;
        CalcDist o = (CalcDist) other;
        return left.equals(o.left) && right.equals(o.right);
    }

    @Override
    public String toString() {
        return "CalcDist(" + left + "," + right + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        left._resolve(mgr);
        right._resolve(mgr);
    }

}
