package config.behaviour.condition;

// ObjEqual
public final class ObjEqual implements config.behaviour.condition.Condition {
    @Override
    public config.behaviour.Conditionenum type() {
        return config.behaviour.Conditionenum.OBJEQUAL;
    }

    private config.behaviour.expobj.ExpObj left;
    private config.behaviour.expobj.ExpObj right;

    private ObjEqual() {
    }

    public ObjEqual(config.behaviour.expobj.ExpObj left, config.behaviour.expobj.ExpObj right) {
        this.left = left;
        this.right = right;
    }

    public static ObjEqual _create(configgen.genjava.ConfigInput input) {
        ObjEqual self = new ObjEqual();
        self.left = config.behaviour.expobj.ExpObj._create(input);
        self.right = config.behaviour.expobj.ExpObj._create(input);
        return self;
    }

    public config.behaviour.expobj.ExpObj getLeft() {
        return left;
    }

    public config.behaviour.expobj.ExpObj getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(left, right);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ObjEqual))
            return false;
        ObjEqual o = (ObjEqual) other;
        return left.equals(o.left) && right.equals(o.right);
    }

    @Override
    public String toString() {
        return "ObjEqual(" + left + "," + right + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        left._resolve(mgr);
        right._resolve(mgr);
    }

}
