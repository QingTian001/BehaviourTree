package behaviour.node.decorator;

import behaviour.BehaviourHelper;
import config.behaviour.Statusenum;
import behaviour.BehaviourFactory;
import behaviour.BehaviourStack;
import behaviour.BehaviourTree;
import behaviour.node.Node;
import util.StringUtil;


public abstract class Decorator<T extends config.behaviour.Node> extends Node<T> {

    private Node<? extends config.behaviour.Node> node = null;
    private BehaviourStack decorateNodeStack = null;

    public Decorator(BehaviourTree behaviourTree, T nodeCfg) {
        super(behaviourTree, nodeCfg);
    }

    @Override
    public void loadFromCfg() {
        config.behaviour.Node decorateNodeCfg = getDecorateNodeCfg();
        node = BehaviourFactory.createNode(getBehaviourTree(), decorateNodeCfg);
        decorateNodeStack = new BehaviourStack();
    }

    protected abstract boolean isValidOnFinish();

    protected abstract config.behaviour.Node getDecorateNodeCfg();

    protected abstract Statusenum decorate(Statusenum childUpdateStatus);

    @Override
    protected Statusenum internalUpdate(BehaviourStack stack) {
        Statusenum status;
        if (node.getStatus() == Statusenum.BTRUNNING) {
            status = decorateNodeStack.updateRunningNode(this);
        } else {
            status = node.update(decorateNodeStack);
        }
        if (!isValidOnFinish() || status != Statusenum.BTRUNNING) {
            Statusenum statusenum = decorate(status);
            if (BehaviourHelper.isNodeFinished(statusenum)) {
                interruptDecorateNodeStack();
            }
            return statusenum;
        }
        return Statusenum.BTRUNNING;
    }

    @Override
    protected Statusenum internalUpdate(BehaviourStack stack, Statusenum childStatus) {
        throw new RuntimeException("should not reach here");
    }

    @Override
    public void reset(boolean recursive) {
        super.reset(recursive);
        node.reset(true);
        decorateNodeStack.reset();
    }

    private void interruptDecorateNodeStack() {
        decorateNodeStack.popAndInterruptRunningNodeAll();
    }

    @Override
    protected void onInterrupt() {
        interruptDecorateNodeStack();
    }

    @Override
    public void dump(int tableNum, StringBuilder dumpTo) {
        super.dump(tableNum, dumpTo);
        String tableStr = StringUtil.getTableString(tableNum);
        dumpTo.append(tableStr).append("{\n");
        dumpTo.append(tableStr).append("Decorator:\n");
        node.dump(tableNum + 1, dumpTo);
        dumpTo.append(tableStr).append("}\n");
    }
}
