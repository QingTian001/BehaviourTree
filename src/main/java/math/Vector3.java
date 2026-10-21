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

    public float distXZ(Vector3 other) {
        float dx = x - other.x, dz = z - other.z;
        return (float) Math.sqrt(dx * dx + dz * dz);
    }
    public boolean Equal(Vector3 other) {
        return Math.abs(x-other.x) < 0.000001f && Math.abs(y-other.y) < 0.000001f && Math.abs(z-other.z) < 0.000001f;
    }
    public Vector3 Sub2(Vector3 other) { return new Vector3(x-other.x, y-other.y, z-other.z); }
    /**
     *
     * @return 返回和z轴的逆时针夹角
     */
    public double toXZAngle() {
        if (x == 0 && z == 0) { throw new UnsupportedOperationException(); }
        double angle = Math.toDegrees(Math.atan2(x, z));
        return angle < 0 ? angle + 360 : angle;
    }
    // 按逆时针旋转, 需要构建x轴向量
    // 不可变向量返回新坐标，调用方需接收返回值。
    public Vector3 relocateReverseXZ(Vector3 axis) {
        float m = (float) Math.sqrt(axis.x * axis.x + axis.z * axis.z);
        return new Vector3((x*axis.x+z*axis.z)/m, y, (z*axis.x-x*axis.z)/m);
    }
    // 按顺时针旋转, 需要构建x轴向量
    public Vector3 relocateXZ(Vector3 axis) {
        float m = (float) Math.sqrt(axis.x * axis.x + axis.z * axis.z);
        return new Vector3((x*axis.x-z*axis.z)/m, y, (z*axis.x+x*axis.z)/m);
    }

    @Override
    public String toString() { return String.format("v3:{%s,%s,%s}", x, y, z); }
}
