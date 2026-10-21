package area;

import math.Vector3;

/**
 * Created by zyao on 2022/2/14 14:45
 */
public class AreaOnePoint extends Area{

    public final Vector3 point;
    public final float dir;

    public AreaOnePoint(Vector3 point, float dir) {
        this.point = point;
        this.dir = dir;
    }

    @Override
    public boolean isInArea(Vector3 center, float radius) {
        return this.point.Dist(center) < radius;
    }

    @Override
    public boolean isInArea(Vector3 center) {
        return this.point.DistSquare(center) < 0.01f;
    }

    @Override
    public Vector3 genRandomPoint() {
        return point.Clone();
    }

    @Override
    public float genRandomDir() {
        return dir;
    }

    @Override
    public void accept(IAreaVisitor visitor) {
        visitor.visit(this);
    }
}
