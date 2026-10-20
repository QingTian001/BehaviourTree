package behaviour.task.impl.action.actions.time;

import config.behaviour.Statusenum;
import behaviour.BehaviourContext;
import behaviour.runtime.action.actions.time.ActWaitRandomTimeMsRuntime;
import behaviour.task.ActionTask;
import behaviour.task.NodeTask;
import behaviour.node.action.actions.time.ActWaitRandomTimeMs;

/**
 * ActWaitRandomTimeMs 的运行时任务实现
 */
public class ActWaitRandomTimeMsTask extends ActionTask implements ActWaitRandomTimeMsRuntime {

    // 运行时状态
    private long startTimeMs = 0;
    private double timeMs = 0;

    public ActWaitRandomTimeMsTask(ActWaitRandomTimeMs atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public ActWaitRandomTimeMs getAtom() {
        return (ActWaitRandomTimeMs) super.getAtom();
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
