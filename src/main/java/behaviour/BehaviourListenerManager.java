package behaviour;

import behaviour.GEntity;
import behaviour.runtime.ListenerHandle;
import behaviour.runtime.ListenerManager;
import behaviour.task.BehaviourTreeTask;
import behaviour.task.ConditionTask;
import event.IMapObjectEventListener;
import event.MapObjectEvent;

import java.util.HashMap;
import java.util.Map;

public class BehaviourListenerManager {
    private final ListenerManager listenerManager;
    private final BehaviourTreeTask behaviourTree;
    private final Runnable dirtyCallback;
    private final Map<GEntity, Map<Class<? extends MapObjectEvent>, ListenerHandle>> entity2ListenerMap = new HashMap<>();

    private BehaviourListenerManager(
            ListenerManager listenerManager,
            BehaviourTreeTask behaviourTree,
            Runnable dirtyCallback) {
        this.listenerManager = listenerManager;
        this.behaviourTree = behaviourTree;
        this.dirtyCallback = dirtyCallback;
    }

    private BehaviourListenerManager(ConditionTask condTask) {
        this(condTask.getRuntimeListenerManager(), condTask.getBehaviourTreeTask(), condTask::setDirty);
    }

    public BehaviourTreeTask getBehaviourTree() {
        return behaviourTree;
    }

    public static BehaviourListenerManager createBehaviourListenerManager(ConditionTask condTask) {
        return condTask == null? null : new BehaviourListenerManager(condTask);
    }

    public static BehaviourListenerManager createBehaviourListenerManager(
            ListenerManager listenerManager,
            BehaviourTreeTask behaviourTree,
            Runnable dirtyCallback) {
        if (listenerManager == null) {
            throw new IllegalArgumentException("listenerManager is null");
        }
        if (behaviourTree == null) {
            throw new IllegalArgumentException("behaviourTree is null");
        }
        if (dirtyCallback == null) {
            throw new IllegalArgumentException("dirtyCallback is null");
        }
        return new BehaviourListenerManager(listenerManager, behaviourTree, dirtyCallback);
    }

    public <Event extends MapObjectEvent> void registerMapObjectEvent(GEntity entity, Class<Event> eventClazz) {
        registerMapObjectEvent(entity, eventClazz, null);
    }

    public <Event extends MapObjectEvent> void registerMapObjectEvent(GEntity entity, Class<Event> eventClazz, IMapObjectEventListener<Event> eventListener) {
        var listener = new IMapObjectEventListener<Event>() {
            @Override
            public void onTriggerEvent(Event event) {
                // 这里统一负责脏标；业务 listener 只处理额外副作用。
                dirtyCallback.run();
                if (eventListener != null) {
                    eventListener.onTriggerEvent(event);
                }
            }
        };
        this.getBehaviourTree().log(org.apache.logging.log4j.Level.INFO, this.getClass(), "BehaviourListenerManager.registerMapObjectEvent: entity:{} , event:{}, listener:{}", entity.getInstanceId(), eventClazz, listener);

        var listenerSet = entity2ListenerMap.computeIfAbsent(entity, e -> new HashMap<>());
        ListenerHandle oldHandle = listenerSet.put(eventClazz, null);
        if (oldHandle != null) {
            oldHandle.dispose();
        }
        ListenerHandle handle = listenerManager.listen(entity, eventClazz, listener);
        listenerSet.put(eventClazz, handle);
    }

    public void onReset() {
        listenerManager.reset();
        entity2ListenerMap.clear();
    }
}
