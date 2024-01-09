package event;

import behaviour.GEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GEntityEventManager {

    private Map<GEntity, EventManager> eventManagerMap = new HashMap<>();
    private static GEntityEventManager instance = new GEntityEventManager();
    public static GEntityEventManager getInstance() {
        return instance;
    }
    public <T extends MapObjectEvent> void registerListener(GEntity entity, Class<T> clazz, IMapObjectEventListener<T> listener) {

        EventManager manager = eventManagerMap.computeIfAbsent(entity, gEntity -> new EventManager());

        manager.registerMapObjectEventListener(clazz, listener);
    }

    public <T extends MapObjectEvent> void unregisterListener(GEntity entity, Class<T> clazz, IMapObjectEventListener<T> listener) {

        EventManager manager = eventManagerMap.computeIfAbsent(entity, gEntity -> new EventManager());

        manager.unregisterMapObjectEventListener(clazz, listener);
    }
}
