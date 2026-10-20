package behaviour.task.impl.node.decorator;

import behaviour.BehaviourContext;
import behaviour.runtime.decorator.DecTimedRuntime;
import behaviour.task.StatusDecoratorTask;
import behaviour.task.ExpressionNumberTask;
import behaviour.task.NodeTask;
import behaviour.node.decorator.DecTimed;

/**
 * DecTimed 的运行时任务实现
 * 定时装饰器：在指定时间内持续执行被装饰节点
 */
public class DecTimedTask extends StatusDecoratorTask implements DecTimedRuntime {
    // 开始时间（从 Atom 迁移过来的运行时状态）
    private long startTimeMs = 0;
    // 持续时间（从 Atom 迁移过来的运行时状态）
    private double timeMs = 0;
    // 时间表达式任务
    private ExpressionNumberTask timeMsExprTask;

    public DecTimedTask(DecTimed atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public DecTimed getAtom() {
        return (DecTimed) super.getAtom();
    }

    public void setTimeMsExprTask(ExpressionNumberTask task) {
        this.timeMsExprTask = task;
    }

    public ExpressionNumberTask getTimeMsExprTask() {
        return timeMsExprTask;
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
