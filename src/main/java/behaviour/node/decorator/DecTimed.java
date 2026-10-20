package behaviour.node.decorator;

import config.behaviour.node.Node;
import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionNumber;

/**
 * Created by zyao on 2023/12/21 18:08
 *
 * 重构后：DecTimed 为纯静态配置类 (Flyweight)
 * - 移除了运行时状态（startTimeMs, timeMs）
 * - 保留静态配置：timeMsExpr
 */
public class DecTimed extends StatusDecorator<config.behaviour.node.DecTimed> {
    // Static configuration only - expression for time calculation
    private ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> timeMsExpr = null;

    public DecTimed(config.behaviour.node.DecTimed atomCfg) {
        super(atomCfg);
    }

    @Override
    protected config.behaviour.node.Node getDecorateNodeCfg() {
        return getAtomCfg().getNode();
    }

    @Override
    public boolean isApplyOnFinishOnly() {
        return getAtomCfg().getValidOnFinish();
    }

    @Override
    public void loadFromCfg() {
        super.loadFromCfg();
        timeMsExpr = BehaviourFactory.createExpression(getAtomCfg().getTimeMs());
    }

    /**
     * Get the time expression for runtime calculation
     */
    public ExpressionNumber<? extends config.behaviour.expnumber.ExpNumber> getTimeMsExpr() {
        return timeMsExpr;
    }

    /**
     * 创建 DecTimedTask 运行时任务
     */
}
