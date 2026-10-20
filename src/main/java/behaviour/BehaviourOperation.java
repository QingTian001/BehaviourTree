package behaviour;

import config.behaviour.Statusenum;

/** 宿主操作只返回运行、成功、失败；取消幂等且终态后无副作用。 */
public interface BehaviourOperation {
    Statusenum update();
    void cancel();
}
