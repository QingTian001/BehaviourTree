package behaviour.task.impl.node.decorator;

import behaviour.BehaviourContext;
import behaviour.runtime.node.decorator.DecLoopRuntime;
import behaviour.task.StatusDecoratorTask;
import behaviour.task.NodeTask;
import behaviour.node.decorator.DecLoop;

/**
 * DecLoop 的运行时任务实现
 * 循环执行被装饰节点，可配置循环次数
 */
public class DecLoopTask extends StatusDecoratorTask implements DecLoopRuntime {
    // 循环计数器（从 Atom 迁移过来的运行时状态）
    private int loopNum = 0;

    public DecLoopTask(DecLoop atom, NodeTask parentTask, BehaviourContext context) {
        super(atom, parentTask, context);
    }

    @Override
    public DecLoop getAtom() {
        return (DecLoop) super.getAtom();
    }

    @Override
    public int getLoopNum() {
        return loopNum;
    }

    @Override
    public void setLoopNum(int loopNum) {
        this.loopNum = loopNum;
    }
}
