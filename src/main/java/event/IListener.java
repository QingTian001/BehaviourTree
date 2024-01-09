package event;

public interface IListener<T extends Event> {

    @SuppressWarnings("unchecked")
    default void onEvent(Event event) {
        onTriggerEvent((T)event);
    }

    void onTriggerEvent(T Event );
}
