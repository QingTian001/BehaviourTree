package behaviour.node.action.actions.move;

import config.behaviour.expobj.ExpObj;
import behaviour.BehaviourFactory;
import behaviour.expression.object.ExpressionGEntity;
import behaviour.node.action.Action;

/**
 * Created by zyao on 2024/9/24 16:49
 *
 * 重构后：纯静态配置类
 */
public class ActFaceToEntity extends Action<config.behaviour.node.ActFaceToEntity> {
    public static final int DURATION_MS = 1000;

    private ExpressionGEntity<? extends ExpObj> targetExpr;

    public ActFaceToEntity(config.behaviour.node.ActFaceToEntity atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        targetExpr = BehaviourFactory.createExpression(getAtomCfg().getEntity());
    }

    public ExpressionGEntity<? extends ExpObj> getTargetExpr() {
        return targetExpr;
    }
}
