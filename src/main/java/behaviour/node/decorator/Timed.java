package behaviour.node.decorator;

import config.behaviour.Statusenum;
import behaviour.BehaviourFactory;
import behaviour.BehaviourTree;
import behaviour.expression.ExpressionNumber;


public class Timed extends Decorator<config.behaviour.node.Timed>{

    private long startTimeMs  = 0;
    private double timeMs = 0;

    private ExpressionNumber<? extends config.behaviour.ExpNumber> timeMsExpr = null;
    public Timed(BehaviourTree behaviourTree, config.behaviour.Node nodeCfg) {
        super(behaviourTree, nodeCfg);
    }

    @Override
    protected boolean isValidOnFinish() {
        return getNodeCfg().getValidOnFinish();
    }

    @Override
    protected config.behaviour.Node getDecorateNodeCfg() {
        return getNodeCfg().getNode();
    }

    @Override
    protected Statusenum decorate(Statusenum childUpdateStatus) {

        //long nowMs = getBehaviourTree().getEntity().getMap().getNowMills();
        long nowMs = System.currentTimeMillis();
        if (nowMs - startTimeMs >= timeMs) {
            return Statusenum.BTSUCCESS;
        }
        return Statusenum.BTRUNNING;
    }

    @Override
    public void loadFromCfg() {
        super.loadFromCfg();
        timeMsExpr = BehaviourFactory.createExpression(this.getBehaviourTree(), getNodeCfg().getTimeMs(), null);
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
        super.reset(true);
        startTimeMs = 0;
        timeMs = 0;
    }
}
