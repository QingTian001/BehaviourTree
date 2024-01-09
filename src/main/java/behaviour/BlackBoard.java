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

    @SuppressWarnings("unchecked")
    public <T> T getValue(String bbKey, T def) {
        if (!valueMap.containsKey(bbKey)) {
            valueMap.put(bbKey, def);
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
            valueMap.put(bbKey, def);
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

    private void internalPutValue(String bbKey, Object obj) {
        valueMap.put(bbKey, obj);
        for (IBBListener<?> listener : getListeners(bbKey)) {
            listener.onBlackBoardChange(bbKey, obj);
        }
    }

    public void putValue(String bbKey, Integer v) {
        putValue(bbKey, (double)v);
    }

    public void putValue(String bbKey, Long v) {
        putValue(bbKey, (double)v);
    }

    public void putValue(String bbKey, Float v) {
        putValue(bbKey, (double)v);
    }

    public void putValue(String bbKey, double v) {
        internalPutValue(bbKey, v);
    }

    public void putValue(String bbKey, String v) {
        internalPutValue(bbKey, v);
    }

    public void putValue(String bbKey, Object v) {
        if (v instanceof Integer) {
            putValue(bbKey, (Integer)v);
        } else if (v instanceof Float) {
            putValue(bbKey, (Float)v);
        } else if (v instanceof Long) {
            putValue(bbKey, (Long)v);
        } else {
            internalPutValue(bbKey, v);
        }
    }
}
