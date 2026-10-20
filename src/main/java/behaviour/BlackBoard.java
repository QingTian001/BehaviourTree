package behaviour;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import behaviour.GEntity;
import behaviour.task.BehaviourTreeTask;
import util.StringUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by zyao on 2023/12/19 17:26
 */
public final class BlackBoard {
    public static final Logger logger = LogManager.getLogger(BlackBoard.class);
    public interface IBBListener<T> {
        @SuppressWarnings("unchecked")
        default void onBlackBoardChange(String bbKey, Object value) {
            onBlackBoard(bbKey, (T)value);
        }
        void onBlackBoard(String bbKey, T bbValue);
    }
    private final GEntity entity;
    // Trace 通过树任务直连，不放进 globalListeners，避免 clear() 时被业务监听器清掉。
    private final BehaviourTreeTask traceTreeTask;
    private final Map<String, Object> valueMap = new HashMap<>();
    private final Map<String, Set<IBBListener<?>>> bbListeners = new HashMap<>();
    // 全局黑板监听, 任何 key 变化都会触发
    private final Set<IBBListener<?>> globalListeners = new HashSet<>();

    public BlackBoard(GEntity entity) {
        this(entity, null);
    }

    public BlackBoard(GEntity entity, BehaviourTreeTask traceTreeTask) {
        this.entity = entity;
        this.traceTreeTask = traceTreeTask;
    }

    public GEntity getEntity() {
        return entity;
    }
    public void clear() {
        if (!valueMap.isEmpty() && traceTreeTask != null) {
            // 清空前发送一次 CLEAR，客户端随后删除该树实例下的全部黑板值。
            traceTreeTask.traceBlackBoardClear();
        }
        valueMap.clear();
        bbListeners.clear();
        globalListeners.clear();
    }

    public long getLongValue(String bbKey, long def) {
        Number value = getValue(bbKey, def);
        return value.longValue();
    }

    public long getLongValue(String bbKey) {
        Number value = getValue(bbKey);
        return value.longValue();
    }
    public int getIntValue(String bbKey, int def) {
        Number value = getValue(bbKey, def);
        return value.intValue();
    }

    public int getIntValue(String bbKey) {
        Number value = getValue(bbKey);
        return value.intValue();
    }

    public float getFloatValue(String bbKey, float def) {
        Number value = getValue(bbKey, def);
        return value.floatValue();
    }

    public float getFloatValue(String bbKey) {
        Number value = getValue(bbKey);
        return value.floatValue();
    }

    public double getDoubleValue(String bbKey, float def) {
        Number value = getValue(bbKey, def);
        return value.doubleValue();
    }

    public double getDoubleValue(String bbKey) {
        Number value = getValue(bbKey);
        return value.doubleValue();
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
        logger.info("register listener for bbKey: {}, entityInstId:{}", bbKey, entity != null ? entity.getInstanceId() : "null");
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

    public void addGlobalListener(IBBListener<?> listener) {
        globalListeners.add(listener);
    }

    public void removeGlobalListener(IBBListener<?> listener) {
        globalListeners.remove(listener);
    }

    private void internalPutValue(String bbKey, Object obj, boolean trigger) {
        if (bbKey.isEmpty()) {
            throw new IllegalArgumentException("bbKey is not set");
        }
        Object oldValue = valueMap.put(bbKey, obj);
        if (traceTreeTask != null && !Objects.equals(oldValue, obj)) {
            // 保留原有条件监听的通知语义，Trace 额外只发送实际值变化。
            traceTreeTask.traceBlackBoardSet(bbKey, obj);
        }
        for (IBBListener<?> listener : globalListeners) {
            listener.onBlackBoardChange(bbKey, obj);
        }
        if (trigger) {
            for (IBBListener<?> listener : getListeners(bbKey)) {
                listener.onBlackBoardChange(bbKey, obj);
            }

        }
    }

    public void putValue(String bbKey, Object v) {
        putValue(bbKey, v, true);
    }

    /**
     * 删除黑板中的一个 key。
     *
     * @return key 原先是否存在
     */
    public boolean removeValue(String bbKey) {
        if (bbKey.isEmpty()) {
            throw new IllegalArgumentException("bbKey is not set");
        }
        if (!valueMap.containsKey(bbKey)) {
            return false;
        }
        valueMap.remove(bbKey);
        if (traceTreeTask != null) {
            traceTreeTask.traceBlackBoardRemove(bbKey);
        }
        for (IBBListener<?> listener : globalListeners) {
            listener.onBlackBoardChange(bbKey, null);
        }
        for (IBBListener<?> listener : getListeners(bbKey)) {
            listener.onBlackBoardChange(bbKey, null);
        }
        return true;
    }

    public boolean isEmpty() {
        return valueMap.isEmpty();
    }

    private void putValue(String bbKey, Object v, boolean trigger) {
        internalPutValue(bbKey, v, trigger);
    }

    public void dump(int tableNum, StringBuilder dumpTo) {
        String tableStr = StringUtil.getTableString(tableNum);
        dumpTo.append(tableStr).append("BlackBoard:").append("{\n");
        String newTableStr = StringUtil.getTableString(tableNum + 1);
        for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
            dumpTo.append(newTableStr).append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        dumpTo.append(tableStr).append("}").append("\n");
    }

    /**
     * 快照全部变量为字符串map, 用于dump输出
     */
    public Map<String, String> dumpVariables() {
        Map<String, String> variables = new HashMap<>();
        valueMap.forEach((key, value) -> variables.put(key, String.valueOf(value)));
        return variables;
    }

    public static void main(String[] args) {
        BlackBoard bb = new BlackBoard(null);
        bb.putValue("test", 1);

        long v = bb.getLongValue("test", 2);
        float v2 = bb.getFloatValue("test", 2);
        System.out.println(v);
        System.out.println(v2);

        long v3 = bb.getLongValue("test1", 2);
        System.out.println(v3);
    }
}
