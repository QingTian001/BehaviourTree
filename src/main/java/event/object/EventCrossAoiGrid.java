package event.object;

import aoi.Grid;
import behaviour.GEntity;
import event.MapObjectEvent;

public class EventCrossAoiGrid extends MapObjectEvent {
    public final Grid leaveGrid;
    public final Grid enterGrid;

    public EventCrossAoiGrid(GEntity entity, Grid leaveGrid, Grid enterGrid) {
        super(entity);

        this.leaveGrid = leaveGrid;
        this.enterGrid = enterGrid;
    }

}
