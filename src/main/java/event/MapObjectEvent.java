package event;


import behaviour.GEntity;

public class MapObjectEvent extends Event {
    public final GEntity entity;
    public MapObjectEvent(GEntity entity) {
        this.entity = entity;
    }
}
