package behaviour.task;

import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.node.decorator.Decorator;
import behaviour.runtime.DecoratorRuntime;
import behaviour.runtime.NodeRuntime;
import util.StringUtil;

/**
 * 装饰节点运行时任务基类，只负责被装饰子节点的生命周期。
 */
public abstract class DecoratorTask extends NodeTask implements DecoratorRuntime {
    private NodeRuntime decoratedNodeRuntime;

    protected DecoratorTask(Decorator<?> atom, BehaviourTask<?> parent, BehaviourContext context) {
        super(atom, parent, context);
    }

    @Override
    public Decorator<?> getAtom() {
        return (Decorator<?>) super.getAtom();
    }

    @Override
    public NodeRuntime getDecoratedNodeRuntime() {
        return decoratedNodeRuntime;
    }

    @Override
    public void setDecoratedNodeRuntime(NodeRuntime decoratedNodeRuntime) {
        this.decoratedNodeRuntime = decoratedNodeRuntime;
    }

    @Override
    public void init() {
        if (getAtom().getDecoratedNode() != null) {
            setDecoratedNodeRuntime(BehaviourFactory.createNodeRuntime(getAtom().getDecoratedNode(), this, getContext()));
        }
    }

    @Override
    public void dump(int tableNum, StringBuilder dumpTo, boolean withCfg) {
        dumpSelf(tableNum, dumpTo, withCfg);
        String tableStr = StringUtil.getTableString(tableNum);
        dumpTo.append(tableStr).append("{\n");
        dumpTo.append(tableStr).append("Decorator:\n");
        dumpDecoratedNode(tableNum + 1, dumpTo, withCfg);
        dumpTo.append(tableStr).append("}\n");
    }

    protected final void dumpDecoratedNode(int tableNum, StringBuilder dumpTo, boolean withCfg) {
        asNodeTask(decoratedNodeRuntime).dump(tableNum, dumpTo, withCfg);
    }

    private static NodeTask asNodeTask(NodeRuntime runtime) {
        if (!(runtime instanceof NodeTask)) {
            throw new RuntimeException("node runtime type error:" + runtime);
        }
        return (NodeTask) runtime;
    }
}

