package event.object;
import behaviour.GEntity;
import event.MapObjectEvent;
public final class EventObjectLeaveMap extends MapObjectEvent {
    public EventObjectLeaveMap(GEntity entity) { super(entity); }
}
