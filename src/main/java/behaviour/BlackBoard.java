package behaviour;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class BlackBoard {
    public interface IBBListener<T> {
        @SuppressWarnings("unchecked")
        default void onBlackBoardChange(String bbKey, Object value) {
            onBlackBoard(bbKey, (T)value);
        }
        void onBlackBoard(String bbKey, T bbValue);
    }
    private final BehaviourTree behaviourTree;
    private final Map<String, Object> valueMap = new HashMap<>();
    private final Map<String, Set<IBBListener<?>>> bbListeners = new HashMap<>();
    public BlackBoard(BehaviourTree behaviourTree) {
        this.behaviourTree = behaviourTree;
    }

    public void reset() {
        valueMap.clear();
        bbListeners.clear();
    }

    public long getLongValue(String bbKey, long def) {
        Object value = getValue(bbKey, def);
        return (long)(double)value;
    }

    public long getLongValue(String bbKey) {
        Object value = getValue(bbKey);
        return (long)(double)value;
    }
    public int getIntValue(String bbKey, int def) {
        Object value = getValue(bbKey, def);
        return (int)(double)value;
    }

    public int getIntValue(String bbKey) {
        Object value = getValue(bbKey);
        return (int)(double)value;
    }

    public float getFloatValue(String bbKey, float def) {
        Object value = getValue(bbKey, def);
        return (float)(double)value;
    }

    public float getFloatValue(String bbKey) {
        Object value = getValue(bbKey);
        return (float)(double)value;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String bbKey, T def) {
        if (bbKey.isEmpty()) {
            throw new IllegalArgumentException("bbKey is empty");
        }
        if (!valueMap.containsKey(bbKey)) {
            //valueMap.put(bbKey, def);
            putValue(bbKey, def, false);
        }
        return (T)valueMap.get(bbKey);
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String bbKey) {
        return (T)valueMap.get(bbKey);
    }

    @SuppressWarnings("unchecked")
    public <T> T getValueAndRegisterListener(String bbKey, T def, IBBListener<T> listener) {
        if (!valueMap.containsKey(bbKey)) {
            //valueMap.put(bbKey, def);
            putValue(bbKey, def, false);
        }
        getListeners(bbKey).add(listener);
        return (T)valueMap.get(bbKey);
    }

    @SuppressWarnings("unchecked")
    public <T> T getValueAndRegisterListener(String bbKey, IBBListener<T> listener) {
        T value = (T)valueMap.get(bbKey);
        getListeners(bbKey).add(listener);
        return value;
    }

    public <T> void unregisterListener(String bbKey, IBBListener<T> listener) {
        getListeners(bbKey).remove(listener);
    }

    private Set<IBBListener<?>> getListeners(String bbKey) {
        return bbListeners.computeIfAbsent(bbKey, s -> new HashSet<>());
    }

    private void internalPutValue(String bbKey, double v, boolean trigger) {
        internalPutValue(bbKey, (Double)v, trigger);
    }

    private void internalPutValue(String bbKey, Object obj, boolean trigger) {
        if (bbKey.isEmpty()) {
            throw new IllegalArgumentException("bbKey is not set");
        }
        valueMap.put(bbKey, obj);
        if (trigger) {
            for (IBBListener<?> listener : getListeners(bbKey)) {
                listener.onBlackBoardChange(bbKey, obj);
            }
        }
    }

    public void putValue(String bbKey, Object v) {
        putValue(bbKey, v, true);
    }

    // 方法内的double强转虽然IDEA判定是灰色, 但是不能删, 否则调不到double类型的internalPutValue方法
    private void putValue(String bbKey, Object v, boolean trigger) {
        if (v instanceof Integer) {
            internalPutValue(bbKey, (double)(int)v, trigger);
        } else if (v instanceof Float) {
            internalPutValue(bbKey, (double)(float)v, trigger);
        } else if (v instanceof Long) {
            internalPutValue(bbKey, (double)(long)v, trigger);
        } else {
            internalPutValue(bbKey, v, trigger);
        }
    }
}
