package testsupport;

import behaviour.GEntity;
import behaviour.runtime.ListenerHandle;
import event.IMapObjectEventListener;
import event.object.EventMove;
import event.object.EventObjectLeaveMap;
import math.Vector3;
import state.*;
import summon.*;
import summon.create.EntityCreateStrategy;
import trigger.TriggerHost;
import java.util.*;
import static testsupport.TreeFixture.Entity;

/** 仅供测试和示例使用的内存场景，真实工程实现三个 Host 接口。 */
public final class SceneFixture implements StateHost, SummonHost, TriggerHost {
    public final TreeFixture.Host behaviour = new TreeFixture.Host();
    public final Map<Integer, StateConfig> states = new HashMap<>();
    public final Map<Integer, SummonConfig> summons = new HashMap<>();
    public final Map<GEntity, Long> lifeDeadlines = new HashMap<>();
    public final Map<GEntity, Long> ownerDeadlines = new HashMap<>();
    public final Map<GEntity, Float> directions = new HashMap<>();
    public final Set<GEntity> publicSummons = new HashSet<>();
    public final List<GEntity> seeing = new ArrayList<>();
    public final List<Integer> createdAiParams = new ArrayList<>();
    public int stateNotices;
    public int leaveCalls;
    public boolean skipCreation;
    private long nextId = 100;

    public long getNowMills() { return behaviour.now; }
    public StateConfig getStateConfig(int id) { return Objects.requireNonNull(states.get(id)); }
    public boolean isActiveAction(int id) { return id < 100; }
    public ListenerHandle listenInterrupt(GEntity entity, int actionId, Runnable interrupted) {
        IMapObjectEventListener<EventMove> listener = event -> interrupted.run();
        entity.registerListener(EventMove.class, listener);
        return () -> entity.unregisterListener(EventMove.class, listener);
    }
    public void onStateChanged(GEntity entity, StateManager manager, Collection<? extends State<?,?,?>> added,
            Collection<? extends State<?,?,?>> removed, Collection<? extends State<?,?,?>> updated) { stateNotices++; }
    public SummonConfig getSummonConfig(int id) { return Objects.requireNonNull(summons.get(id)); }
    public float getDirection(GEntity entity) { return directions.getOrDefault(entity, 0f); }
    public EntityCreateStrategy getEntityCreateStrategy(int objectType) {
        if (objectType != 1) { throw new IllegalArgumentException("示例仅实现对象类型 1"); }
        return (unit, position, direction, cfgId, param) -> {
            if (skipCreation) { return null; }
            Entity entity = new Entity(++nextId);
            entity.position = position;
            directions.put(entity, direction);
            createdAiParams.add(unit.getAiParam());
            return entity;
        };
    }
    public void enterMap(GEntity entity) { behaviour.entities.put(entity.getInstanceId(), (Entity) entity); }
    public void leaveMap(GEntity entity) {
        Entity child = (Entity) entity;
        if (child.destroyed) { return; }
        leaveCalls++;
        child.destroyed = true;
        behaviour.entities.remove(entity.getInstanceId());
        seeing.remove(entity);
        lifeDeadlines.remove(entity); ownerDeadlines.remove(entity);
        directions.remove(entity); publicSummons.remove(entity);
        entity.triggerEvent(new EventObjectLeaveMap(entity));
    }
    public void configureSummon(GEntity entity, GEntity owner, boolean aoiPublic, long lifeDeadline) {
        setOwner(entity, owner);
        if (aoiPublic) { publicSummons.add(entity); }
        if (lifeDeadline > 0) { lifeDeadlines.put(entity, lifeDeadline); }
    }
    public void setOwner(GEntity entity, GEntity owner) { ((Entity) entity).ownerId = owner == null ? 0 : owner.getInstanceId(); }
    public void setOwnerLeaveDeadline(GEntity entity, long deadline) { ownerDeadlines.put(entity, deadline); }
    public Iterable<? extends GEntity> getSeeingEntities(GEntity observer) { return seeing; }
    public int getEntityType(GEntity entity) { return 1; }
    public void advanceTo(long now) {
        behaviour.now = now;
        Set<GEntity> expired = new HashSet<>();
        lifeDeadlines.forEach((entity, deadline) -> { if (deadline <= now) expired.add(entity); });
        ownerDeadlines.forEach((entity, deadline) -> { if (deadline <= now) expired.add(entity); });
        for (GEntity entity : expired) { leaveMap(entity); }
    }
    public static SummonConfig summon(int id, boolean destroyWithOwner, long life, long ownerTimeout) {
        return new SummonConfig(id,1,true,life,3,2,List.of(new SummonUnit(10,7,1),new SummonUnit(20,8,1)),
                Util.POSITION_TYPE_SELF,new Vector3(0,0,0),180,2,0,2,0,Util.ROTATION_OWNER_ROTATION,0,destroyWithOwner,ownerTimeout);
    }
}
