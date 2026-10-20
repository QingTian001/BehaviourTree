package config.behaviour.expnumber;

// RandInteger
public final class RandInteger implements config.behaviour.expnumber.ExpNumber {
    @Override
    public config.behaviour.Expnumberenum type() {
        return config.behaviour.Expnumberenum.RANDINTEGER;
    }

    private int min;
    private int max;

    private RandInteger() {
    }

    public RandInteger(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static RandInteger _create(configgen.genjava.ConfigInput input) {
        RandInteger self = new RandInteger();
        self.min = input.readInt();
        self.max = input.readInt();
        return self;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(min, max);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RandInteger))
            return false;
        RandInteger o = (RandInteger) other;
        return min == o.min && max == o.max;
    }

    @Override
    public String toString() {
        return "RandInteger(" + min + "," + max + ")";
    }

}
