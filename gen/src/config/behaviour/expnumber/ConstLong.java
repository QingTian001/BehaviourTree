package config.behaviour.expnumber;

public class ConstLong implements config.behaviour.ExpNumber {
    @Override
    public config.behaviour.Expnumberenum type() {
        return config.behaviour.Expnumberenum.CONSTLONG;
    }

    private long value;

    private ConstLong() {
    }

    public ConstLong(long value) {
        this.value = value;
    }

    public static ConstLong _create(configgen.genjava.ConfigInput input) {
        ConstLong self = new ConstLong();
        self.value = input.readLong();
        return self;
    }

    public long getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ConstLong))
            return false;
        ConstLong o = (ConstLong) other;
        return value == o.value;
    }

    @Override
    public String toString() {
        return "ConstLong(" + value + ")";
    }

}
