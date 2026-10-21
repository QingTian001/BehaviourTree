package manager;

import behaviour.GEntity;
import state.State;

/**
 * Created by zyao on 2022/3/14 14:47
 */

@FunctionalInterface
public interface IStateFinishHandle {
    void handle(GEntity entity, State<?, ?, ?> state, boolean isNormalEnd);
}
