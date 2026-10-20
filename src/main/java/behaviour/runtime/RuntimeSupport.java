package behaviour.runtime;

import org.apache.logging.log4j.Level;
import behaviour.BehaviourContext;

public interface RuntimeSupport {
    BehaviourContext getContext();

    void reset(boolean recursive);

    BehaviourTreeRuntime getBehaviourTreeRuntime();

    void setDirty();

    ListenSupport listeners();

    void resetListeners();

    void log(Level level, String msg);
}
