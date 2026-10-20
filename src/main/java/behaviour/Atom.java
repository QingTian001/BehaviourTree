package behaviour;

import behaviour.executor.Executor;
import behaviour.task.BehaviourTask;
import util.StringUtil;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Flyweight base class for all behaviour tree atoms.
 * <p>
 * Atom is a global singleton (Flyweight pattern) containing:
 * - Static configuration (atomCfg)
 * - Bound executor reference
 * <p>
 * Runtime state is stored in Task classes, created on demand.
 *
 * @param <T> The configuration type
 */
public abstract class Atom<T> {
    protected final T atomCfg;
    private static final AtomicLong idGen = new AtomicLong();
    private final long runtimeId = idGen.getAndIncrement();
    private Executor<?> executor;
    private String path;
    private long pathId;

    public Atom(T atomCfg) {
        this.atomCfg = atomCfg;
    }

    public T getAtomCfg() {
        return atomCfg;
    }

    /** 运行时自增 id，仅作调试参考，不跨重启稳定 */
    public long getRuntimeId() {
        return runtimeId;
    }

    /** 稳定 id：树内路径的 FNV-1a 64 哈希；未分配路径时回退运行时 id */
    public long getId() {
        if (path == null) {
            throw new IllegalStateException("path not assigned for atom: " + getClass().getName());
        }
        return pathId;
    }

    /** 从 rootNode 起的完整路径，如 node-CompParallel/nodes[2]-ActWaitTimeMs */
    public String getPath() {
        return path;
    }

    /** 稳定 id 的 16 位小写十六进制表示 */
    public String getPathIdHex() {
        return String.format("%016x", getId());
    }

    /** 由 NodePath.assignTree 在建树后统一分配 */
    void setPath(String path, long pathId) {
        this.path = path;
        this.pathId = pathId;
    }

    public final void bindExecutor(Executor<?> executor) {
        if (executor == null) {
            throw new IllegalArgumentException("executor is null");
        }
        if (this.executor != null) {
            throw new IllegalStateException("executor already bound for atom: " + getClass().getName());
        }
        this.executor = executor;
    }

    @SuppressWarnings("unchecked")
    public final <E extends Executor<?>> E getExecutor(Class<E> executorClass) {
        if (executor == null) {
            throw new IllegalStateException("executor not bound for atom: " + getClass().getName());
        }
        if (!executorClass.isInstance(executor)) {
            throw new IllegalStateException("executor bound for atom: " + getClass().getName()
                    + ", but type mismatch. expected: " + executorClass.getName()
                    + ", actual: " + executor.getClass().getName());
        }
        return (E) executor;
    }

    public abstract BehaviourTask<?> createRuntime(BehaviourTask<?> parent, BehaviourContext context);

    /**
     * Load static configuration from config.
     * Called once when creating the Flyweight instance.
     */
    public abstract void loadFromCfg();

    public String getCfgNote()  {
        Class<?> clazz = atomCfg.getClass();
        try {
            Field noteField = clazz.getDeclaredField("note");
            noteField.setAccessible(true);
            return StringUtil.format("({})", (String) noteField.get(atomCfg));
        } catch (NoSuchFieldException e) {
            return "";
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public final String toString(boolean withCfg) {
        return StringUtil.format("{}({})-{}{}", getRuntimeId(), getPathIdHex(), getClass().getSimpleName(), withCfg? getCfgNote() : "");
    }

    public final void dump(int tableNum, StringBuilder dumpTo, boolean withCfg) {
        String tableStr = StringUtil.getTableString(tableNum);
        dumpTo.append(StringUtil.format("{}{}\n", tableStr, toString(withCfg)));
    }
}
