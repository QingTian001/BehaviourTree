package aoi;
import math.Vector3;
/** 宿主格子描述。同一 level/x/z 的 equals/hashCode 必须稳定。 */
public interface Grid {
    int getGridX();
    int getGridZ();
    int getAoiLevel();
    Vector3 getLeftBottom();
    Vector3 getLeftTop();
    Vector3 getRightTop();
    Vector3 getRightBottom();
    /** 格子是左闭右开区间，零点属于 0 号格，负坐标向下取整。 */
    static int getAoiGridAxis(float axis, int length) { return (int) Math.floor(axis / length); }
}
