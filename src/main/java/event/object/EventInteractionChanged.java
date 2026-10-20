package event.object;
import behaviour.GEntity;
import event.MapObjectEvent;
public final class EventInteractionChanged extends MapObjectEvent {
    public EventInteractionChanged(GEntity entity) { super(entity); }
}
