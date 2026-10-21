package summon.posstrategy;

import behaviour.GEntity;
import math.Vector3;

import java.util.List;

/**
 * Created by zyao on 2025/9/9 11:03
 */
public interface PositionTypeStrategy {
    Vector3 calculateBasePosition();
    void checkAndBindSummonEntity(List<GEntity> summonEntities);
    float getBaseDir();
}
