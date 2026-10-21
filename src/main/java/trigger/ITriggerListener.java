package trigger;

import behaviour.GEntity;

public interface ITriggerListener {
    void triggerIn(GEntity entity);
    void triggerOut(GEntity entity);
}
