package config.behaviour.expstr;

public class Const implements config.behaviour.ExpStr {
    @Override
    public config.behaviour.Expstrenum type() {
        return config.behaviour.Expstrenum.CONST;
    }

    private String value;

    private Const() {
    }

    public Const(String value) {
        this.value = value;
    }

    public static Const _create(configgen.genjava.ConfigInput input) {
        Const self = new Const();
        self.value = input.readStr();
        return self;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Const))
            return false;
        Const o = (Const) other;
        return value.equals(o.value);
    }

    @Override
    public String toString() {
        return "Const(" + value + ")";
    }

}
