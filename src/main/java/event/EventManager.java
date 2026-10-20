package event;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class EventManager {
    private final Map<Class<? extends Event>, LinkedHashMap<IListener<? extends Event>, Object>> listenerMap = new HashMap<>();

    public <T extends MapObjectEvent> void registerMapObjectEventListener(Class<T> type, IMapObjectEventListener<T> listener) {
        listenerMap.computeIfAbsent(type, key -> new LinkedHashMap<>()).putIfAbsent(listener, new Object());
    }

    public <T extends MapObjectEvent> void unregisterMapObjectEventListener(Class<T> type, IMapObjectEventListener<T> listener) {
        var listeners = listenerMap.get(type);
        if (listeners == null) { return; }
        listeners.remove(listener);
        if (listeners.isEmpty()) { listenerMap.remove(type); }
    }

    public void triggerEvent(Event event) {
        var listeners = listenerMap.get(event.getClass());
        if (listeners == null) { return; }
        // 令牌区分注销后重新注册，当前分派不执行新注册项。
        var snapshot = new LinkedHashMap<>(listeners);
        for (var entry : snapshot.entrySet()) {
            var current = listenerMap.get(event.getClass());
            if (current != null && current.get(entry.getKey()) == entry.getValue()) {
                entry.getKey().onEvent(event);
            }
        }
    }

    boolean isEmpty() { return listenerMap.isEmpty(); }
}
