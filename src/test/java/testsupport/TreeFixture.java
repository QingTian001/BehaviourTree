package testsupport;

import behaviour.*;
import behaviour.task.BehaviourTreeTask;
import config.behaviour.BBParam;
import config.behaviour.Behaviour;
import config.behaviour.node.Node;
import config.behaviour.Statusenum;
import event.*;
import math.Vector3;
import java.util.*;

public final class TreeFixture {
    public static final class Entity implements GEntity {
        public final long id;
        public Vector3 position = new Vector3(0, 0, 0);
        public long ownerId;
        public boolean destroyed;
        private final EventManager bus = new EventManager();
        private final Map<Class<?>, Set<Object>> subscriptions = new HashMap<>();
        public Entity(long id) { this.id = id; }
        public long getInstanceId() { return id; }
        public Vector3 getPos() { return position.Clone(); }
        public long getOwnerInstId() { return ownerId; }
        public boolean isDestroyed() { return destroyed; }
        public <T extends MapObjectEvent> void registerListener(Class<T> type, IMapObjectEventListener<T> listener) {
            bus.registerMapObjectEventListener(type, listener);
            subscriptions.computeIfAbsent(type, key -> new HashSet<>()).add(listener);
        }
        public <T extends MapObjectEvent> void unregisterListener(Class<T> type, IMapObjectEventListener<T> listener) {
            bus.unregisterMapObjectEventListener(type, listener);
            var listeners = subscriptions.get(type);
            if (listeners != null) { listeners.remove(listener); }
        }
        public void triggerEvent(MapObjectEvent event) {
            if (event.entity != this) { throw new IllegalArgumentException(); }
            bus.triggerEvent(event);
        }
        public int listenerCount() { return subscriptions.values().stream().mapToInt(Set::size).sum(); }
    }

    public static final class Operation implements BehaviourOperation {
        public Statusenum status = Statusenum.BTRUNNING;
        public int cancelCalls;
        public Statusenum update() { return status; }
        public void cancel() { cancelCalls++; }
    }

    public static final class Host implements BehaviourHost {
        public long now;
        public boolean reject;
        public boolean interacting;
        public final Map<Long, Entity> entities = new LinkedHashMap<>();
        public final List<Operation> operations = new ArrayList<>();
        public List<? extends GEntity> candidates = List.of();
        public GEntity movedEntity, interactionTarget;
        public Vector3 destination;
        public Float direction;
        public int abilityId, interactionId, objectType;
        public long dataInteractionId;
        public boolean enter;
        public List<Integer> configIds;
        public int stopCalls;
        public long getNowMills() { return now; }
        public GEntity getEntity(long id) { return entities.get(id); }
        public Iterable<? extends GEntity> findEntities(GEntity self, int type, List<Integer> ids, float radius) {
            objectType = type;
            configIds = ids;
            return candidates;
        }
        public BehaviourOperation moveTo(GEntity entity, Vector3 position, Float dir) {
            movedEntity = entity; destination = position; direction = dir;
            return operation();
        }
        public void stopMove(GEntity entity) { movedEntity = entity; stopCalls++; }
        public void changeDirection(GEntity entity, float dir) { movedEntity = entity; direction = dir; }
        public BehaviourOperation interact(GEntity self, GEntity target, int ability, int interaction, long data, boolean entering) {
            interactionTarget = target; abilityId = ability; interactionId = interaction;
            dataInteractionId = data; enter = entering;
            return operation();
        }
        public boolean isInInteraction(GEntity self, GEntity target, int ability, int interaction) { return interacting; }
        private Operation operation() {
            if (reject) { return null; }
            Operation op = new Operation(); operations.add(op); return op;
        }
    }

    public static Behaviour config(Node node) {
        try {
            var constructor = Behaviour.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Behaviour cfg = constructor.newInstance();
            Map<String, Object> fields = Map.of("id", 10001, "name", "测试树", "objType", 1, "debug", false,
                    "params", List.of(), "returnValue", new BBParam(1, ""), "node", node, "note", "");
            for (var entry : fields.entrySet()) {
                var field = Behaviour.class.getDeclaredField(entry.getKey());
                field.setAccessible(true); field.set(cfg, entry.getValue());
            }
            return cfg;
        } catch (ReflectiveOperationException e) { throw new AssertionError(e); }
    }
    public static BehaviourTreeTask tree(Node node, Entity self, Host host) {
        return new BehaviourTree(config(node)).createRuntime(self, host);
    }
}
