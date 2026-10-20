package behaviour.node.action.actions.move;

import config.behaviour.expvector3.ExpVector3;
import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionVector3;
import behaviour.node.action.Action;

/**
 * Created by zyao on 2024/3/19 17:25
 *
 * 重构后：纯静态配置类
 */
public class ActWalkToPos extends Action<config.behaviour.node.ActWalkToPos> {
    private ExpressionVector3<? extends ExpVector3> posExpression;

    public ActWalkToPos(config.behaviour.node.ActWalkToPos atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        posExpression = BehaviourFactory.createExpression(this.getAtomCfg().getPos());
    }

    public ExpressionVector3<? extends ExpVector3> getPosExpression() {
        return posExpression;
    }
}
