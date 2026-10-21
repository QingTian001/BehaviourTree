package area;

import math.Vector3;
import util.MathUtil;

/**
 * Created by zyao on 2025/3/1 15:09
 */
public class AreaFan extends Area {
    // 角度都是z轴正方向的逆时针夹角, 角度范围是[startAngle - angleRange, startAngle + angleRange]
    private final Vector3 center;         // 扇形的中心点
    private final float radius;           // 扇形的半径
    private final float startAngle;       // 扇形跟起始角度（扇形的中心角度）
    private final float halfAngleRange;   // 扇形的半展开角度范围

    public AreaFan(Vector3 center, float radius, float startAngle, float halfAngleRange) {
        this.center = center;
        this.radius = radius;
        this.startAngle = startAngle;
        this.halfAngleRange = halfAngleRange;
    }

    public Vector3 getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }
    public float getFanAngle() {
        return halfAngleRange * 2;
    }

    public float getHalfAngleRange() {
        return halfAngleRange;
    }

    public float getStartAngle() {
        return startAngle;
    }

    @Override
    public boolean isInArea(Vector3 center, float radius) {
        return false;
    }

    // 判断一个点是否在扇形内
    @Override
    public boolean isInArea(Vector3 point) {
        float distance = center.distXZ(point);
        if (distance > radius) {
            return false;
        }
        if (point.Equal(center)) {
            return true;
        }
        Vector3 centerToPoint = point.Sub2(center);
        float angle = (float)centerToPoint.toXZAngle();
        if (halfAngleRange >= 180.0f) {
            return true;
        }
        float angleStart = normalizeAngle(startAngle - halfAngleRange);
        float angleEnd = normalizeAngle(startAngle + halfAngleRange);

        if (angleStart <= angleEnd) {
            return angle >= angleStart && angle <= angleEnd;
        }
        return angle >= angleStart || angle <= angleEnd;
    }

    /**
     * 将角度归一化到 [0, 360) 范围。
     */
    private static float normalizeAngle(float angle) {
        float normalized = angle % 360.0f;
        return normalized < 0.0f ? normalized + 360.0f : normalized;
    }

    @Override
    public Vector3 genRandomPoint() {
        float angle = MathUtil.randomFloat(halfAngleRange * 2) + (startAngle - halfAngleRange);
        float randomRadius = MathUtil.randFloatBetween(0.001f, radius); // 避免生成到圆心
        double radian = angle * Math.PI / 180;
        float x = center.x + randomRadius * (float) Math.sin(radian);
        float z = center.z + randomRadius * (float) Math.cos(radian);

        return new Vector3(x, center.y, z);
    }

    @Override
    public void accept(IAreaVisitor visitor) {
        visitor.visit(this);
    }
}
