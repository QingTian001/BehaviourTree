package summon;
import java.util.List;
import math.Vector3;
/** 召唤配置快照，generator 的 summonType 与参数 SummonType 位掩码相互独立。 */
public record SummonConfig(int id,
        int objType,
        boolean aoiPublic,
        long lifetime,
        int summonType,
        int num,
        List<SummonUnit> units,
        int positionType,
        Vector3 position,
        float summonAngle,
        float radius,
        float baseOffsetAngle,
        int distributionType,
        float height,
        int summonDirType,
        float dir,
        boolean destroyWithOwner,
        long destroyTimeMsAfterOwner) {
    public SummonConfig { units = List.copyOf(units); }
    public int getId() { return id; }
    public int getObjType() { return objType; }
    public boolean getAoiPublic() { return aoiPublic; }
    public long getLifetime() { return lifetime; }
    public int getSummonType() { return summonType; }
    public int getNum() { return num; }
    public List<SummonUnit> getUnits() { return units; }
    public int getPositionType() { return positionType; }
    public Vector3 getPosition() { return position; }
    public float getSummonAngle() { return summonAngle; }
    public float getRadius() { return radius; }
    public float getBaseOffsetAngle() { return baseOffsetAngle; }
    public int getDistributionType() { return distributionType; }
    public float getHeight() { return height; }
    public int getSummonDirType() { return summonDirType; }
    public float getDir() { return dir; }
    public boolean getDestroyWithOwner() { return destroyWithOwner; }
    public long getDestroyTimeMsAfterOwner() { return destroyTimeMsAfterOwner; }
}
