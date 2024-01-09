package config.behaviour.condition;

public class ObjEqual implements config.behaviour.Condition {
    @Override
    public config.behaviour.Conditionenum type() {
        return config.behaviour.Conditionenum.OBJEQUAL;
    }

    private config.behaviour.ExpObj left;
    private config.behaviour.ExpObj right;

    private ObjEqual() {
    }

    public ObjEqual(config.behaviour.ExpObj left, config.behaviour.ExpObj right) {
        this.left = left;
        this.right = right;
    }

    public static ObjEqual _create(configgen.genjava.ConfigInput input) {
        ObjEqual self = new ObjEqual();
        self.left = config.behaviour.ExpObj._create(input);
        self.right = config.behaviour.ExpObj._create(input);
        return self;
    }

    public config.behaviour.ExpObj getLeft() {
        return left;
    }

    public config.behaviour.ExpObj getRight() {
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

}
