package behaviour.task;

import org.apache.logging.log4j.Level;
import behaviour.BehaviourHost;
import behaviour.GEntity;
import behaviour.Atom;
import behaviour.BehaviourContext;
import behaviour.BlackBoard;
import behaviour.executor.Executor;
import behaviour.runtime.BehaviourTreeRuntime;
import behaviour.runtime.ListenSupport;
import behaviour.runtime.ListenerHandle;
import behaviour.runtime.ListenerManager;
import behaviour.runtime.RuntimeSupport;
import event.IMapObjectEventListener;
import event.MapObjectEvent;
import util.StringUtil;

import java.lang.reflect.Field;

/**
 * Base class for all runtime Task classes.
 * Task holds runtime state, delegates logic to Atom (Flyweight).
 *
 * @param <T> The type of Atom this task is associated with
 */
public abstract class BehaviourTask<T extends Atom<?>> implements RuntimeSupport, ListenSupport {
    private final T atom;
    private final Executor<?> executor;
    private final BehaviourTask<?> parent;
    private final BehaviourContext context;
    private final ListenerManager runtimeListenerManager = new ListenerManager();

    public BehaviourTask(T atom, BehaviourTask<?> parent, BehaviourContext context) {
        this.atom = atom;
        this.executor = atom.getExecutor(Executor.class);
        this.parent = parent;
        this.context = context;
    }

    /**
     * Get associated Atom (Flyweight configuration)
     */
    public T getAtom() {
        return atom;
    }

    public Executor<?> getExecutor() {
        return executor;
    }

    @SuppressWarnings("unchecked")
    public <E extends Executor<?>> E getExecutor(Class<E> executorClass) {
        if (executor == null || !executorClass.isInstance(executor)) {
            return null;
        }
        return (E) executor;
    }

    public <E extends Executor<?>> E requireExecutor(Class<E> executorClass) {
        E typedExecutor = getExecutor(executorClass);
        if (typedExecutor == null) {
            throw new RuntimeException("executor not found. task:" + getClass().getName()
                    + ", atom:" + atom.getClass().getName()
                    + ", expected:" + executorClass.getName());
        }
        return typedExecutor;
    }

    /**
     * Get parent task
     */
    public BehaviourTask<?> getParent() {
        return parent;
    }

    /**
     * Get runtime context
     */
    @Override
    public BehaviourContext getContext() {
        return context;
    }

    public final ListenerManager getRuntimeListenerManager() {
        return runtimeListenerManager;
    }

    @Override
    public void setDirty() {
    }

    @Override
    public ListenSupport listeners() {
        return this;
    }

    public final BehaviourTreeTask getBehaviourTreeTask() {
        return context.getBehaviourTreeTask();
    }

    @Override
    public final BehaviourTreeRuntime getBehaviourTreeRuntime() {
        return context.getBehaviourTreeTask();
    }

    // Convenience access methods

    public GEntity getSelf() {
        return context.getSelf();
    }

    public BlackBoard getBlackBoard() {
        return context.getBlackBoard();
    }

    public BehaviourHost getHost() { return context.getHost(); }

    protected final String getLogHead() {
        return StringUtil.format("path:{} entityIId:{} btCId:{}", getAtom().getPath(),
                getSelf() == null ? 0 : getSelf().getInstanceId(), getBehaviourTreeTask().getId());
    }

    @Override
    public void log(Level level, String msg) {
        this.context.getBehaviourTreeTask().log(level, this.getClass(), StringUtil.format("{}; Class:{} {}", getLogHead(), this.getClass().getSimpleName(), msg));
    }

    protected final void dumpSelf(int tableNum, StringBuilder dumpTo, boolean withCfg) {
        String tableStr = StringUtil.getTableString(tableNum);
        dumpTo.append(StringUtil.format("{}{}{}\n", tableStr, getAtom().toString(withCfg), getRunningInfo()));
    }

    public String getRunningInfo() {
        return "";
    }

    public void dump(int tableNum, StringBuilder dumpTo, boolean withCfg) {
        dumpSelf(tableNum, dumpTo, withCfg);
    }

    /**
     * Optional initialization hook for child runtime tree construction.
     */
    public void init() {
    }

    @Override
    public final <Event extends MapObjectEvent> ListenerHandle listen(
            GEntity entity,
            Class<Event> eventClazz,
            IMapObjectEventListener<Event> eventListener) {
        ListenerHandle handle = runtimeListenerManager.listen(
                entity,
                eventClazz,
                eventListener,
                () -> log(Level.INFO, StringUtil.format("unregisterListener. entity:{}, event:{}, listener:{}",
                        entity.getInstanceId(), eventClazz.getSimpleName(), eventListener)));
        log(Level.INFO, StringUtil.format("registerListener. entity:{}, event:{}, listener:{}",
                entity.getInstanceId(), eventClazz.getSimpleName(), eventListener));
        return handle;
    }

    protected final void disposeRuntimeListeners() {
        runtimeListenerManager.reset();
    }

    @Override
    public void resetListeners() {
        disposeRuntimeListeners();
    }

    /**
     * Reset runtime state. Called when node finishes.
     * Delegates to Atom's reset method.
     *
     * @param recursive if true, reset child tasks recursively
     */
    public abstract void reset(boolean recursive);

    @Override
    public String toString() {
        return StringUtil.format("{}@{}", getClass().getSimpleName(), getAtom().getId());
    }
}
