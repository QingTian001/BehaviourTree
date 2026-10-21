package area;

import math.Vector3;
import util.MathUtil;

/**
 * Created by zyao on 2022/4/28 11:06
 */
public class AreaRectangle extends Area{

    private final Vector3 relocateCenter;
    private final Vector3 newVectorX;
    private final float xWidth;
    private final float zWidth;

    public AreaRectangle(Vector3 center, Vector3 newVectorX, float xWidth, float zWidth) {
        relocateCenter = center.relocateReverseXZ(newVectorX);
        this.newVectorX = newVectorX;
        this.xWidth = xWidth;
        this.zWidth = zWidth;
    }

    public AreaRectangle(Vector3 leftBottom, Vector3 rightTop) {
        this(new Vector3((leftBottom.x + rightTop.x) / 2, (leftBottom.y + rightTop.y) / 2, (leftBottom.z + rightTop.z) / 2),
        new Vector3(1, 0, 0), Math.abs(rightTop.x - leftBottom.x), Math.abs(rightTop.z - leftBottom.z));
    }

    public Vector3 getRelocateCenter() {
        return relocateCenter;
    }

    public Vector3 getNewVectorX() {
        return newVectorX;
    }

    public float getxWidth() {
        return xWidth;
    }

    public float getzWidth() {
        return zWidth;
    }

    public boolean isRotation() {
        return !(newVectorX.x == 1 && newVectorX.z == 0);
    }

    @Override
    public boolean isInArea(Vector3 center, float radius) {
        return false;
    }

    @Override
    public boolean isInArea(Vector3 center) {
        Vector3 v = center.relocateReverseXZ(newVectorX);

        return v.x <= relocateCenter.x + xWidth / 2 && v.x >= relocateCenter.x - xWidth / 2 && v.z <= relocateCenter.z + zWidth / 2 && v.z >= relocateCenter.z - zWidth / 2;
    }

    @Override
    public Vector3 genRandomPoint() {

        float deltaX = MathUtil.randomFloat( xWidth) - xWidth / 2;
        float deltaZ = MathUtil.randomFloat( zWidth) - zWidth / 2;

        Vector3 v = new Vector3(relocateCenter.x + deltaX, relocateCenter.y, relocateCenter.z + deltaZ);
        return v.relocateXZ(newVectorX);
    }

    @Override
    public void accept(IAreaVisitor visitor) {
        visitor.visit(this);
    }

    public static Vector3 dirToNewVectorX(float dir) {
        float rad = (float) Math.toRadians(dir);
        return new Vector3((float)Math.cos(rad), 0, (float)-Math.sin(rad));
    }
}
