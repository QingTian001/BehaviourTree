package event.object;

import behaviour.GEntity;
import event.MapObjectEvent;

public class EventLeaveAoi extends MapObjectEvent {

    public final GEntity leaveEntity;

    public EventLeaveAoi(GEntity mainEntity, GEntity leaveEntity) {
        super(mainEntity);

        this.leaveEntity = leaveEntity;
    }

}
