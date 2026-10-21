package event.object;

import behaviour.GEntity;
import state.State;
import event.MapObjectEvent;

/**
 * 实体状态加入成功后触发的内部事件。
 */
public class EventStateAdded extends MapObjectEvent {
    // 已经加入 StateManager 的状态实例，监听方可按需建立运行态索引。
    public final State<?, ?, ?> state;

    public EventStateAdded(GEntity entity, State<?, ?, ?> state) {
        super(entity);
        this.state = state;
    }
}
