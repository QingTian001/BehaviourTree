package behaviour.expression;


import behaviour.BehaviourTree;
import behaviour.GEntity;
import behaviour.condition.Condition;
import event.GEntityEventManager;
import event.IMapObjectEventListener;
import event.MapObjectEvent;


import java.util.HashMap;
import java.util.Map;

public abstract class Expression<T> {

    private final T exprCfg;

    // 由Condition使用的时候会设置Condition
    private final Condition<? extends config.behaviour.Condition> condition;

    private final Map<GEntity, Map<Class<? extends MapObjectEvent>, IMapObjectEventListener<? extends MapObjectEvent>>> entity2ListenerMap = new HashMap<>();

    public final T getExprCfg() {
        return exprCfg;
    }

    public final BehaviourTree behaviourTree;

    public final Condition<? extends config.behaviour.Condition> getCondition() {
        return condition;
    }
    public Expression(BehaviourTree behaviourTree, T exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        this.exprCfg = exprCfg;
        this.condition = condition;
        this.behaviourTree = behaviourTree;
    }

    public BehaviourTree getBehaviourTree() {
        return behaviourTree;
    }
    private <Event extends MapObjectEvent> void registerMapObjectEvent(GEntity entity, Class<Event> eventClazz) {
        if (condition == null) {
            throw new RuntimeException("condtion must be not null");
        }

        var listener = new IMapObjectEventListener<Event>() {
            @Override
            public void onTriggerEvent(Event event) {
                condition.setDirty();
            }
        };
        GEntityEventManager.getInstance().registerListener(entity, eventClazz, listener);

        var listenerSet = entity2ListenerMap.computeIfAbsent(entity, e -> new HashMap<>());
        listenerSet.put(eventClazz, listener);

    }

    @SuppressWarnings("unchecked")
    public void reset() {
        // 清理事件
        entity2ListenerMap.forEach((entity, iMapObjectEventListeners) -> {
            for (var entry : iMapObjectEventListeners.entrySet()) {
                GEntityEventManager.getInstance().unregisterListener(entity, (Class<MapObjectEvent>)entry.getKey(), (IMapObjectEventListener<MapObjectEvent>)entry.getValue());
            }
        });
    }

    public void loadFromCfg() {

    }
}
