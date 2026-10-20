package event;

import behaviour.GEntity;
import math.Vector3;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class EventDispatchTest {
    static final class Entity implements GEntity {
        public long getInstanceId() { return 1; }
        public Vector3 getPos() { return new Vector3(0, 0, 0); }
        public long getOwnerInstId() { return 0; }
        public boolean isDestroyed() { return false; }
    }

    @Test void removalDuringDispatchSkipsRemovedListenerAndAdditionWaitsUntilNextDispatch() {
        EventManager bus = new EventManager();
        List<String> calls = new ArrayList<>();
        IMapObjectEventListener<MapObjectEvent> late = e -> calls.add("late");
        IMapObjectEventListener<MapObjectEvent> removed = e -> calls.add("removed");
        bus.registerMapObjectEventListener(MapObjectEvent.class, e -> {
            calls.add("first");
            bus.unregisterMapObjectEventListener(MapObjectEvent.class, removed);
            bus.registerMapObjectEventListener(MapObjectEvent.class, late);
        });
        bus.registerMapObjectEventListener(MapObjectEvent.class, removed);
        bus.triggerEvent(new MapObjectEvent(new Entity()));
        assertEquals(List.of("first"), calls);
        bus.triggerEvent(new MapObjectEvent(new Entity()));
        assertEquals(List.of("first", "first", "late"), calls);
    }

    @Test void selfRemovalAndNestedDispatchAreSafe() {
        EventManager bus = new EventManager();
        List<String> calls = new ArrayList<>();
        IMapObjectEventListener<MapObjectEvent> first = new IMapObjectEventListener<>() {
            public void onTriggerEvent(MapObjectEvent e) {
                calls.add("first");
                bus.unregisterMapObjectEventListener(MapObjectEvent.class, this);
                bus.triggerEvent(e);
            }
        };
        bus.registerMapObjectEventListener(MapObjectEvent.class, first);
        bus.registerMapObjectEventListener(MapObjectEvent.class, e -> calls.add("second"));
        bus.triggerEvent(new MapObjectEvent(new Entity()));
        assertEquals(List.of("first", "second", "second"), calls);
    }

    @Test void lastUnregisterReleasesEntityAndTriggerDoesNotRetainUnknownEntity() throws Exception {
        GEntityEventManager bus = GEntityEventManager.getInstance();
        Entity entity = new Entity();
        IMapObjectEventListener<MapObjectEvent> listener = e -> {};
        bus.registerListener(entity, MapObjectEvent.class, listener);
        bus.unregisterListener(entity, MapObjectEvent.class, listener);
        Field field = GEntityEventManager.class.getDeclaredField("eventManagerMap");
        field.setAccessible(true);
        Map<?, ?> map = (Map<?, ?>) field.get(bus);
        assertFalse(map.containsKey(entity));
        entity.triggerEvent(new MapObjectEvent(entity));
        assertFalse(map.containsKey(entity));
        assertThrows(IllegalArgumentException.class, () -> entity.triggerEvent(new MapObjectEvent(new Entity())));
    }
}
