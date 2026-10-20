package event;

import behaviour.GEntity;
import java.util.IdentityHashMap;
import java.util.Map;

public class GEntityEventManager {
    private final Map<GEntity, EventManager> eventManagerMap = new IdentityHashMap<>();
    private static final GEntityEventManager instance = new GEntityEventManager();
    public static GEntityEventManager getInstance() { return instance; }

    public <T extends MapObjectEvent> void registerListener(GEntity entity, Class<T> type, IMapObjectEventListener<T> listener) {
        eventManagerMap.computeIfAbsent(entity, key -> new EventManager()).registerMapObjectEventListener(type, listener);
    }

    public <T extends MapObjectEvent> void unregisterListener(GEntity entity, Class<T> type, IMapObjectEventListener<T> listener) {
        var manager = eventManagerMap.get(entity);
        if (manager == null) { return; }
        manager.unregisterMapObjectEventListener(type, listener);
        if (manager.isEmpty()) { eventManagerMap.remove(entity); }
    }

    public void triggerEvent(MapObjectEvent event) {
        var manager = eventManagerMap.get(event.entity);
        if (manager != null) { manager.triggerEvent(event); }
    }
}
