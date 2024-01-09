package event;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class EventManager {

    private Map<Class<? extends Event>, LinkedHashSet<IListener<? extends Event>>> listenerMap = new HashMap<>();

    private <T extends Event> void registerListener(Class<T> eventClazz, IListener<T> listener) {
        var listeners = listenerMap.computeIfAbsent(eventClazz, aClass -> new LinkedHashSet<>());
        listeners.add(listener);
    }


    public <T extends MapObjectEvent> void registerMapObjectEventListener(Class<T> eventClazz, IMapObjectEventListener<T> listener) {
        registerListener(eventClazz, listener);
    }


    private <T extends Event> void unregisterListener(Class<T> eventClazz, IListener<T> listener) {
        var listeners = listenerMap.computeIfAbsent(eventClazz, aClass -> new LinkedHashSet<>());
        listeners.remove(listener);
    }

    public <T extends MapObjectEvent> void unregisterMapObjectEventListener(Class<T> eventClazz, IMapObjectEventListener<T> listener) {
        unregisterListener(eventClazz, listener);
    }

    public void triggerEvent(Event event) {

        var listeners = listenerMap.computeIfAbsent(event.getClass(), aClass -> new LinkedHashSet<>());

        for (var listener : listeners) {
            listener.onEvent(event);
        }
    }
}
