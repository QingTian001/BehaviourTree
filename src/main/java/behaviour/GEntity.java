package behaviour;

import event.GEntityEventManager;
import event.IMapObjectEventListener;
import event.MapObjectEvent;
import math.Vector3;

/** 行为树挂载对象；事件由宿主在状态变化后同步分派。 */
public interface GEntity {
    long getInstanceId();
    Vector3 getPos();
    long getOwnerInstId();
    boolean isDestroyed();

    default <T extends MapObjectEvent> void registerListener(Class<T> type, IMapObjectEventListener<T> listener) {
        GEntityEventManager.getInstance().registerListener(this, type, listener);
    }
    default <T extends MapObjectEvent> void unregisterListener(Class<T> type, IMapObjectEventListener<T> listener) {
        GEntityEventManager.getInstance().unregisterListener(this, type, listener);
    }
    default void triggerEvent(MapObjectEvent event) {
        if (event.entity != this) { throw new IllegalArgumentException("事件实体与触发实体不一致"); }
        GEntityEventManager.getInstance().triggerEvent(event);
    }
}
