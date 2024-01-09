package event;

@FunctionalInterface
public interface IMapObjectEventListener<T extends MapObjectEvent> extends IListener<T> {
    void onTriggerEvent(T event);
}
