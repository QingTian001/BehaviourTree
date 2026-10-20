package behaviour.runtime;

import behaviour.GEntity;
import event.IMapObjectEventListener;
import event.MapObjectEvent;

/**
 * Minimal listener registration facade for runtime tasks.
 */
public interface ListenSupport {
    <Event extends MapObjectEvent> ListenerHandle listen(
            GEntity entity,
            Class<Event> eventClazz,
            IMapObjectEventListener<Event> eventListener);
}
