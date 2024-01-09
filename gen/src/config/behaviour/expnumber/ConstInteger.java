package config.behaviour.expnumber;

public class ConstInteger implements config.behaviour.ExpNumber {
    @Override
    public config.behaviour.Expnumberenum type() {
        return config.behaviour.Expnumberenum.CONSTINTEGER;
    }

    private int value;

    private ConstInteger() {
    }

    public ConstInteger(int value) {
        this.value = value;
    }

    public static ConstInteger _create(configgen.genjava.ConfigInput input) {
        ConstInteger self = new ConstInteger();
        self.value = input.readInt();
        return self;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ConstInteger))
            return false;
        ConstInteger o = (ConstInteger) other;
        return value == o.value;
    }

    @Override
    public String toString() {
        return "ConstInteger(" + value + ")";
    }

}
