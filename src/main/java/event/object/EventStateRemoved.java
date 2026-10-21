package event.object;

import behaviour.GEntity;
import state.State;
import event.MapObjectEvent;

/**
 * 实体状态即将从 StateManager 移除时触发的内部事件。
 */
public class EventStateRemoved extends MapObjectEvent {
    // 即将被移除的状态实例，监听方可在 StateManager 删除前清理运行态索引。
    public final State<?, ?, ?> state;

    public EventStateRemoved(GEntity entity, State<?, ?, ?> state) {
        super(entity);
        this.state = state;
    }
}
