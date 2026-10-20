package behaviour.task.impl.action.actions.time;

import config.behaviour.Statusenum;
import behaviour.BehaviourContext;
import behaviour.runtime.action.actions.time.ActWaitTimeMsRuntime;
import behaviour.task.ActionTask;
import behaviour.task.NodeTask;
import behaviour.node.action.actions.time.ActWaitTimeMs;

/**
 * ActWaitTimeMs 的运行时任务实现
 */
public class ActWaitTimeMsTask extends ActionTask implements ActWaitTimeMsRuntime {

    // 运行时状态
    private long startTimeMs = 0;
    private double timeMs = 0;

    public ActWaitTimeMsTask(ActWaitTimeMs atom, NodeTask parent, BehaviourContext context) {
        super(atom, parent, context);
    }

    @Override
    public ActWaitTimeMs getAtom() {
        return (ActWaitTimeMs) super.getAtom();
    }

    public long getStartTimeMs() {
        return startTimeMs;
    }

    public void setStartTimeMs(long startTimeMs) {
        this.startTimeMs = startTimeMs;
    }

    public double getTimeMs() {
        return timeMs;
    }

    public void setTimeMs(double timeMs) {
        this.timeMs = timeMs;
    }
}
