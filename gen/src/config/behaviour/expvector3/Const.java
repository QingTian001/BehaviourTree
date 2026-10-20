package config.behaviour.expvector3;

// Const
public final class Const implements config.behaviour.expvector3.ExpVector3 {
    @Override
    public config.behaviour.Expvector3enum type() {
        return config.behaviour.Expvector3enum.CONST;
    }

    private float x;
    private float y;
    private float z;

    private Const() {
    }

    public Const(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Const _create(configgen.genjava.ConfigInput input) {
        Const self = new Const();
        self.x = input.readFloat();
        self.y = input.readFloat();
        self.z = input.readFloat();
        return self;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y, z);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Const))
            return false;
        Const o = (Const) other;
        return x == o.x && y == o.y && z == o.z;
    }

    @Override
    public String toString() {
        return "Const(" + x + "," + y + "," + z + ")";
    }

}
