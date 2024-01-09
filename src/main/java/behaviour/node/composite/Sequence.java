package behaviour.node.composite;

import config.behaviour.Node;
import config.behaviour.Statusenum;
import behaviour.BehaviourFactory;
import behaviour.BehaviourHelper;
import behaviour.BehaviourStack;
import behaviour.BehaviourTree;
import behaviour.condition.Condition;
import behaviour.node.IPreCondition;

import java.util.List;

public class Sequence extends Composite<config.behaviour.node.Sequence> implements IPreCondition {

    private Condition<? extends config.behaviour.Condition> preCond = null;
    int curRunningIndex = 0;
    public Sequence(BehaviourTree behaviourTree, Node nodeCfg) {
        super(behaviourTree, nodeCfg);
    }

    @Override
    protected Statusenum internalUpdate(BehaviourStack stack) {
        return _update(stack, Statusenum.BTRUNNING);
    }

    @Override
    protected List<? extends Node> getConfigNodeList() {
        return getNodeCfg().getNodes();
    }

    @Override
    public void reset(boolean recursive) {
        curRunningIndex = 0;
        if (preCond != null) {
            preCond.reset(recursive);
        }
        super.reset(recursive);
    }

    @Override
    public void loadFromCfg() {
        super.loadFromCfg();

        if (!BehaviourHelper.isConditionNull(getNodeCfg().getPrecond())) {
            preCond = BehaviourFactory.createCondition(this.getBehaviourTree(), getNodeCfg().getPrecond(), null);
        }
    }

    @Override
    public Condition<? extends config.behaviour.Condition> getPreCondition() {
        return preCond;
    }

    @Override
    protected boolean enter() {
        return preCond == null || preCond.calculateConditionResultAndListenEvent();
    }

    @Override
    protected Statusenum internalUpdate(BehaviourStack stack, Statusenum childStatus) {
        return _update(stack, childStatus);
    }

    private Statusenum _update(BehaviourStack stack, Statusenum childStatus) {
        Statusenum runningStatus = childStatus;
        int i = curRunningIndex;

        while (true) {
            if (runningStatus == Statusenum.BTFAILURE) {
                return Statusenum.BTFAILURE;
            }
            else if (runningStatus == Statusenum.BTRUNNING) {
                var node = getNodeList().get(i);
                runningStatus = node.update(stack);
            }

            if (runningStatus == Statusenum.BTRUNNING) {
                curRunningIndex = i;
                return Statusenum.BTRUNNING;
            }

            if (runningStatus == Statusenum.BTFAILURE) {
                return Statusenum.BTFAILURE;
            }
            if (++i == getNodeList().size()) {
                return Statusenum.BTSUCCESS;
            }
            runningStatus = Statusenum.BTRUNNING;
        }
    }
}
