package behaviour.runtime;

import config.behaviour.Statusenum;
import behaviour.BehaviourStack;

public interface NodeRuntime extends RuntimeSupport {
    Statusenum update(BehaviourStack stack);

    Statusenum update(BehaviourStack stack, Statusenum childStatus);

    void interrupt();

    Statusenum getStatus();

    NodeRuntime getParentRuntime();

    BehaviourTreeRuntime getRootBehaviourTreeRuntime();
}
