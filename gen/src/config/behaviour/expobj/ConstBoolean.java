package config.behaviour.expobj;

// ConstBoolean
public final class ConstBoolean implements config.behaviour.expobj.ExpObj {
    @Override
    public config.behaviour.Expobjenum type() {
        return config.behaviour.Expobjenum.CONSTBOOLEAN;
    }

    private boolean value;

    private ConstBoolean() {
    }

    public ConstBoolean(boolean value) {
        this.value = value;
    }

    public static ConstBoolean _create(configgen.genjava.ConfigInput input) {
        ConstBoolean self = new ConstBoolean();
        self.value = input.readBool();
        return self;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ConstBoolean))
            return false;
        ConstBoolean o = (ConstBoolean) other;
        return value == o.value;
    }

    @Override
    public String toString() {
        return "ConstBoolean(" + value + ")";
    }

}
