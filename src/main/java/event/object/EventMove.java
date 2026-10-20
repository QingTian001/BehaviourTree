package event.object;
import behaviour.GEntity;
import event.MapObjectEvent;
public final class EventMove extends MapObjectEvent {
    public EventMove(GEntity entity) { super(entity); }
}
