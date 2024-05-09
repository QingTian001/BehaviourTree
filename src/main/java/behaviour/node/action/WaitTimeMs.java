package behaviour.node.action;

import config.behaviour.Statusenum;
import behaviour.BehaviourFactory;
import behaviour.BehaviourStack;
import behaviour.BehaviourTree;
import behaviour.expression.ExpressionNumber;

public class WaitTimeMs extends Action<config.behaviour.node.WaitTimeMs> {

    private long startTimeMs  = 0;
    private double timeMs = 0;

    private ExpressionNumber<? extends config.behaviour.ExpNumber> timeMsExpr = null;
    public WaitTimeMs(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
        super(behaviourTree, nodeCfg);
    }


    @Override
    public void loadFromCfg() {
        timeMsExpr = BehaviourFactory.createExpression(this.getBehaviourTree(), getNodeCfg().getTimeMs(), null);
    }

    @Override
    protected Statusenum internalUpdate(BehaviourStack stack) {
        //long nowMs = getBehaviourTree().getEntity().getMap().getNowMills();
        long nowMs = System.currentTimeMillis();
        if (nowMs - startTimeMs >= timeMs) {
            return Statusenum.BTSUCCESS;
        }
        return Statusenum.BTRUNNING;
    }

    @Override
    protected boolean enter() {
        //startTimeMs = getBehaviourTree().getEntity().getMap().getNowMills();
        startTimeMs = System.currentTimeMillis();
        timeMs = timeMsExpr.calculateExpression();
        return true;
    }

    @Override
    public void reset(boolean recursive) {
        super.reset(recursive);
        startTimeMs = 0;
        timeMs = 0;
    }
}
