package state;
import behaviour.GEntity;
import behaviour.runtime.ListenerHandle;
import java.util.Collection;
/** 宿主负责配置、时钟、动作事件及网络/存储通知；均在场景线程调用。 */
public interface StateHost {
    StateConfig getStateConfig(int id);
    long getNowMills();
    boolean isActiveAction(int actionId);
    ListenerHandle listenInterrupt(GEntity entity, int actionId, Runnable interrupted);
    void onStateChanged(GEntity entity, StateManager manager, Collection<? extends State<?,?,?>> added,
            Collection<? extends State<?,?,?>> removed, Collection<? extends State<?,?,?>> updated);
}
