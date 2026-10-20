package behaviour.runtime;

/**
 * Runtime listener handle.
 * Listener resources must be owned by a runtime Task.
 */
public interface ListenerHandle {
    void dispose();
}
