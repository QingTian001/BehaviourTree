package config.behaviour.expnumber;

public class ConstFloat implements config.behaviour.ExpNumber {
    @Override
    public config.behaviour.Expnumberenum type() {
        return config.behaviour.Expnumberenum.CONSTFLOAT;
    }

    private float value;

    private ConstFloat() {
    }

    public ConstFloat(float value) {
        this.value = value;
    }

    public static ConstFloat _create(configgen.genjava.ConfigInput input) {
        ConstFloat self = new ConstFloat();
        self.value = input.readFloat();
        return self;
    }

    public float getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ConstFloat))
            return false;
        ConstFloat o = (ConstFloat) other;
        return value == o.value;
    }

    @Override
    public String toString() {
        return "ConstFloat(" + value + ")";
    }

}
