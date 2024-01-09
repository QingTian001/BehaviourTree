package config.behaviour.condition;

public class StrEqual implements config.behaviour.Condition {
    @Override
    public config.behaviour.Conditionenum type() {
        return config.behaviour.Conditionenum.STREQUAL;
    }

    private config.behaviour.ExpStr left;
    private config.behaviour.ExpStr right;

    private StrEqual() {
    }

    public StrEqual(config.behaviour.ExpStr left, config.behaviour.ExpStr right) {
        this.left = left;
        this.right = right;
    }

    public static StrEqual _create(configgen.genjava.ConfigInput input) {
        StrEqual self = new StrEqual();
        self.left = config.behaviour.ExpStr._create(input);
        self.right = config.behaviour.ExpStr._create(input);
        return self;
    }

    public config.behaviour.ExpStr getLeft() {
        return left;
    }

    public config.behaviour.ExpStr getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(left, right);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StrEqual))
            return false;
        StrEqual o = (StrEqual) other;
        return left.equals(o.left) && right.equals(o.right);
    }

    @Override
    public String toString() {
        return "StrEqual(" + left + "," + right + ")";
    }

}
