package behaviour;

import event.GEntityEventManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.condition.Condition;
import event.IMapObjectEventListener;
import event.MapObjectEvent;

import java.util.HashMap;
import java.util.Map;

public class BehaviourListenerManager {
    private static final Logger logger = LogManager.getLogger(BehaviourListenerManager.class);
    private final Condition<? extends config.behaviour.Condition> cond;
    private final Map<GEntity, Map<Class<? extends MapObjectEvent>, IMapObjectEventListener<? extends MapObjectEvent>>> entity2ListenerMap = new HashMap<>();
    private BehaviourListenerManager(Condition<? extends config.behaviour.Condition> cond) {
        this.cond = cond;
    }

    public static BehaviourListenerManager createBehaviourListenerManager(Condition<? extends config.behaviour.Condition> cond) {
        return cond == null? null : new BehaviourListenerManager(cond);
    }

    public <Event extends MapObjectEvent> void registerMapObjectEvent(GEntity entity, Class<Event> eventClazz) {
        registerMapObjectEvent(entity, eventClazz, null);
    }

    public <Event extends MapObjectEvent> void registerMapObjectEvent(GEntity entity, Class<Event> eventClazz, IMapObjectEventListener<Event> eventListener) {
        var listener = new IMapObjectEventListener<Event>() {
            @Override
            public void onTriggerEvent(Event event) {
                cond.setDirty();
                if (eventListener != null) {
                    eventListener.onTriggerEvent(event);
                }
            }
        };
        logger.info("BehaviourListenerManager.registerMapObjectEvent: entity:{} , event:{}, listener:{}", entity, eventClazz, listener);
        GEntityEventManager.getInstance().registerListener(entity, eventClazz, listener);

        var listenerSet = entity2ListenerMap.computeIfAbsent(entity, e -> new HashMap<>());
        listenerSet.put(eventClazz, listener);
    }

    @SuppressWarnings("unchecked")
    public void onReset() {
        // 清理事件
        entity2ListenerMap.forEach((entity, iMapObjectEventListeners) -> {
            for (var entry : iMapObjectEventListeners.entrySet()) {
                Class<MapObjectEvent> clz = (Class<MapObjectEvent>)entry.getKey();
                var listener = (IMapObjectEventListener<MapObjectEvent>)entry.getValue();
                logger.info("BehaviourListenerManager.onReset: entity:{} , event:{}, listener:{}", entity, clz, listener);
                GEntityEventManager.getInstance().unregisterListener(entity, clz, listener);
            }
        });
    }
}
