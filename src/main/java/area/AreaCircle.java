package area;

import math.Vector3;
import util.MathUtil;

/**
 * Created by zyao on 2022/2/14 14:47
 */
public class AreaCircle extends Area {

    private final Vector3 center;
    private final float radius;

    public AreaCircle(Vector3 center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    public final Vector3 getCenter() {
        return center;
    }

    public final float getRadius() {
        return radius;
    }

    @Override
    public boolean isInArea(Vector3 center, float radius) {
        return this.center.distXZ(center) < this.radius + radius;
    }

    @Override
    public boolean isInArea(Vector3 center) {
        return this.center.distXZ(center) < this.radius;
    }

    @Override
    public Vector3 genRandomPoint() {
        float angle = MathUtil.randomFloat(2 * (float) Math.PI);
        float k = MathUtil.randomFloat();

        float x = (float) (center.x + (Math.sin(angle) * k * radius));
        float z = (float) (center.z + (Math.cos(angle) * k * radius));

        return new Vector3(x, center.y, z);
    }

    @Override
    public void accept(IAreaVisitor visitor) {
        visitor.visit(this);
    }
}
