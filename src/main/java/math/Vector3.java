package math;

/** 独立的不可变位置快照。 */
public final class Vector3 {
    public final float x;
    public final float y;
    public final float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 Clone() { return new Vector3(x, y, z); }
    public float DistSquare(Vector3 other) {
        float dx = x - other.x, dy = y - other.y, dz = z - other.z;
        return dx * dx + dy * dy + dz * dz;
    }
    public float Dist(Vector3 other) { return (float) Math.sqrt(DistSquare(other)); }

    @Override
    public String toString() { return String.format("v3:{%s,%s,%s}", x, y, z); }
}
