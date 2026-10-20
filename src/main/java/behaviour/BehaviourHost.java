package behaviour;

import math.Vector3;
import java.util.List;

/** 接入项目实现的当前场景能力，拒绝操作返回 null。 */
public interface BehaviourHost {
    long getNowMills();
    GEntity getEntity(long instanceId);
    Iterable<? extends GEntity> findEntities(GEntity self, int objectType, List<Integer> configIds, float radius);
    BehaviourOperation moveTo(GEntity entity, Vector3 position, Float direction);
    void stopMove(GEntity entity);
    void changeDirection(GEntity entity, float direction);
    BehaviourOperation interact(GEntity self, GEntity target, int abilityId, int interactionId, long dataInteractionId, boolean enter);
    boolean isInInteraction(GEntity self, GEntity target, int abilityId, int interactionId);
}
