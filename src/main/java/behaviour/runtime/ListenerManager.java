package behaviour.runtime;

import behaviour.GEntity;
import behaviour.BlackBoard;
import event.IMapObjectEventListener;
import event.MapObjectEvent;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * 运行时监听资源管理器。
 * 负责登记监听资源，并在运行时任务重置时统一释放。
 */
public final class ListenerManager {
    private final List<ManagedListenerHandle> handles = new ArrayList<>();
    private final Map<Object, ManagedListenerHandle> listenerToHandle = new IdentityHashMap<>();

    public ListenerHandle track(ListenerHandle handle) {
        if (handle == null) {
            throw new IllegalArgumentException("handle is null");
        }
        ManagedListenerHandle managedHandle = new ManagedListenerHandle(handle, null);
        handles.add(managedHandle);
        return managedHandle;
    }

    public <Event extends MapObjectEvent> ListenerHandle listen(
            GEntity entity,
            Class<Event> eventClass,
            IMapObjectEventListener<Event> listener) {
        return listen(entity, eventClass, listener, null);
    }

    /**
     * 注册实体事件监听，并在注销成功后执行附加回调。
     * 附加回调只用于调用方的诊断日志，不参与监听资源的生命周期管理。
     */
    public <Event extends MapObjectEvent> ListenerHandle listen(
            GEntity entity,
            Class<Event> eventClass,
            IMapObjectEventListener<Event> listener,
            Runnable afterDispose) {
        if (entity == null) {
            throw new IllegalArgumentException("entity is null");
        }
        if (eventClass == null) {
            throw new IllegalArgumentException("eventClass is null");
        }
        if (listener == null) {
            throw new IllegalArgumentException("listener is null");
        }

        disposeListener(listener);
        entity.registerListener(eventClass, listener);
        return trackListener(listener, () -> {
            entity.unregisterListener(eventClass, listener);
            if (afterDispose != null) {
                afterDispose.run();
            }
        });
    }

    public <T> T getValueAndRegisterBlackBoardListener(
            BlackBoard blackBoard,
            String bbKey,
            T defaultValue,
            BlackBoard.IBBListener<T> listener) {
        validateBlackBoardRegistration(blackBoard, bbKey, listener);
        disposeListener(listener);
        T value = blackBoard.getValueAndRegisterListener(bbKey, defaultValue, listener);
        trackListener(listener, () -> blackBoard.unregisterListener(bbKey, listener));
        return value;
    }

    public <T> T getValueAndRegisterBlackBoardListener(
            BlackBoard blackBoard,
            String bbKey,
            BlackBoard.IBBListener<T> listener) {
        validateBlackBoardRegistration(blackBoard, bbKey, listener);
        disposeListener(listener);
        T value = blackBoard.getValueAndRegisterListener(bbKey, listener);
        trackListener(listener, () -> blackBoard.unregisterListener(bbKey, listener));
        return value;
    }

    public void disposeListener(Object listener) {
        if (listener == null) {
            return;
        }
        ManagedListenerHandle handle = listenerToHandle.get(listener);
        if (handle != null) {
            handle.dispose();
        }
    }

    public void reset() {
        RuntimeException firstFailure = null;
        for (ManagedListenerHandle handle : new ArrayList<>(handles)) {
            try {
                handle.dispose();
            } catch (RuntimeException e) {
                if (firstFailure == null) {
                    firstFailure = e;
                }
            }
        }
        handles.clear();
        listenerToHandle.clear();
        if (firstFailure != null) {
            throw firstFailure;
        }
    }

    private ListenerHandle trackListener(Object listener, ListenerHandle handle) {
        ManagedListenerHandle managedHandle = new ManagedListenerHandle(handle, listener);
        handles.add(managedHandle);
        listenerToHandle.put(listener, managedHandle);
        return managedHandle;
    }

    private static <T> void validateBlackBoardRegistration(
            BlackBoard blackBoard,
            String bbKey,
            BlackBoard.IBBListener<T> listener) {
        if (blackBoard == null) {
            throw new IllegalArgumentException("blackBoard is null");
        }
        if (bbKey == null) {
            throw new IllegalArgumentException("bbKey is null");
        }
        if (listener == null) {
            throw new IllegalArgumentException("listener is null");
        }
    }

    private final class ManagedListenerHandle implements ListenerHandle {
        private final ListenerHandle delegate;
        private final Object listener;
        private boolean disposed;

        private ManagedListenerHandle(ListenerHandle delegate, Object listener) {
            this.delegate = delegate;
            this.listener = listener;
        }

        @Override
        public void dispose() {
            if (disposed) {
                return;
            }
            disposed = true;
            try {
                delegate.dispose();
            } finally {
                handles.remove(this);
                if (listener != null && listenerToHandle.get(listener) == this) {
                    listenerToHandle.remove(listener);
                }
            }
        }
    }
}
