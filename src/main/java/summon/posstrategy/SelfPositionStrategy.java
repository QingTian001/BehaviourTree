package summon.posstrategy;

import behaviour.GEntity;
import summon.SummonConfig;
import summon.SummonHost;
import math.Vector3;

import java.util.List;

/**
 * Created by zyao on 2025/9/9 11:03
 */
public class SelfPositionStrategy implements PositionTypeStrategy {
    private final GEntity self;
    private final SummonHost host;

    public SelfPositionStrategy(GEntity self, SummonHost host) {
        this.self = self;
        this.host = host;
    }
    @Override
    public Vector3 calculateBasePosition() {
        return self.getPos().Clone();
    }

    @Override
    public void checkAndBindSummonEntity(List<GEntity> summonEntities) {
        return;
    }

    @Override
    public float getBaseDir() {
        return host.getDirection(self);
    }
}
