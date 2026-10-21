package event.object;

import behaviour.GEntity;
import event.MapObjectEvent;

public class EventEnterAoi extends MapObjectEvent {

    public final GEntity enterEntity;
    public EventEnterAoi(GEntity mainEntity, GEntity enterEntity) {
        super(mainEntity);
        this.enterEntity = enterEntity;
    }
}
