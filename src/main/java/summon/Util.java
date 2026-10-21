package summon;

import summon.SummonConfig;
import summon.SummonUnit;
import behaviour.GEntity;
import summon.posstrategy.PositionTypeStrategy;
import summon.posstrategy.SelfPositionStrategy;
import summon.posstrategy.SetPositionStrategy;
import math.Vector3;
import util.MathUtil;

import java.util.*;

/**
 * Created by zyao on 2025/2/28 11:58
 */
public class Util {
    public static final int POSITION_TYPE_SELF = 2;
    public static final int POSITION_TYPE_SET = 1;
    public static final int POSITION_TYPE_INTERACT_FURNITURE = 3;
    public static final int POSITION_TYPE_SHORE = 4;

    public static final int ROTATION_RANDOM = 1;
    public static final int ROTATION_OWNER_ROTATION = 2;
    public static final int ROTATION_OWNER_ENTITY_LINK_ROTATION = 3;
    public static final int ROTATION_WORLD = 4;


    public static PositionTypeStrategy createPositionStrategy(int positionType, GEntity self, SummonConfig cfg, SummonHost host) {
        return switch (positionType) {
            case POSITION_TYPE_SELF -> new SelfPositionStrategy(self, host);
            case POSITION_TYPE_SET -> new SetPositionStrategy(self, cfg, host);
            default -> throw new IllegalArgumentException("未迁入的位置策略:" + positionType);
        };
    }
    public static int[] getSummonWeights(List<SummonUnit> summonunitListCfg)
    {
        int[] weights = new int[summonunitListCfg.size()];
        for (int i = 0; i < summonunitListCfg.size(); i++) {
            weights[i] = summonunitListCfg.get(i).getWeight();
        }
        return weights;
    }

    public static float getSummonAngle(Vector3 basePos, float baseDir, SummonConfig summonCfg, Vector3 point) {
        if (summonCfg.getSummonDirType() == ROTATION_RANDOM) {
            return MathUtil.randomFloat(360.0f);
        } else if (summonCfg.getSummonDirType() == ROTATION_OWNER_ROTATION) {
            return baseDir + summonCfg.getDir();
        } else if (summonCfg.getSummonDirType() == ROTATION_OWNER_ENTITY_LINK_ROTATION) {
            if (basePos.Equal(point)) {
                return baseDir + summonCfg.getDir();
            } else {
                return MathUtil.getAngle(basePos, point) + summonCfg.getDir();
            }

        } else if (summonCfg.getSummonDirType() == ROTATION_WORLD) {
            return summonCfg.getDir();
        } else {
            throw new RuntimeException("unexpected summon dir type:" + summonCfg.getSummonDirType());
        }
    }

}
