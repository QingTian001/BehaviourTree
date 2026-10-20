package behaviour.executor.node.decorator;

import config.behaviour.Statusenum;
import behaviour.BehaviourHelper;
import behaviour.BehaviourStack;
import behaviour.executor.node.NodeExecutor;
import behaviour.node.decorator.StatusDecorator;
import behaviour.runtime.NodeRuntime;
import behaviour.runtime.node.decorator.StatusDecoratorRuntime;

/**
 * 状态装饰节点执行器基类，负责独立子栈和结果转换生命周期。
 */
public abstract class StatusDecoratorExecutor<A extends StatusDecorator<?>> extends NodeExecutor<A> {
    protected StatusDecoratorExecutor(A atom) {
        super(atom);
    }

    protected abstract Statusenum applyDecoration(
            StatusDecoratorRuntime runtime,
            Statusenum childStatus);

    @Override
    public boolean onEnter(NodeRuntime runtime) {
        return true;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack) {
        StatusDecoratorRuntime decoratorRuntime = (StatusDecoratorRuntime) runtime;
        Statusenum status;
        if (decoratorRuntime.getDecoratedNodeRuntime().getStatus() == Statusenum.BTRUNNING) {
            status = decoratorRuntime.getDecorateNodeStack().updateRunningNodeRuntime(runtime);
        } else {
            status = decoratorRuntime.getDecoratedNodeRuntime().update(decoratorRuntime.getDecorateNodeStack());
        }
        if (!getAtom().isApplyOnFinishOnly() || status != Statusenum.BTRUNNING) {
            Statusenum decoratedStatus = applyDecoration(decoratorRuntime, status);
            if (BehaviourHelper.isNodeFinished(decoratedStatus)) {
                interruptDecorateNodeStack(decoratorRuntime);
            }
            return decoratedStatus;
        }
        return Statusenum.BTRUNNING;
    }

    @Override
    public Statusenum update(NodeRuntime runtime, BehaviourStack stack, Statusenum childStatus) {
        throw new RuntimeException("should not reach here");
    }

    @Override
    public void onInterrupt(NodeRuntime runtime) {
        interruptDecorateNodeStack((StatusDecoratorRuntime) runtime);
    }

    @Override
    public void reset(NodeRuntime runtime, boolean recursive) {
        StatusDecoratorRuntime decoratorRuntime = (StatusDecoratorRuntime) runtime;
        if (decoratorRuntime.getDecoratedNodeRuntime() != null) {
            decoratorRuntime.getDecoratedNodeRuntime().reset(recursive);
        }
        decoratorRuntime.getDecorateNodeStack().reset();
    }

    protected final void interruptDecorateNodeStack(StatusDecoratorRuntime runtime) {
        runtime.getDecorateNodeStack().popAndInterruptRunningNodeAll();
    }
}
