package behaviour.node.action.actions.move;

import config.behaviour.expnumber.ExpNumber;
import config.behaviour.expvector3.ExpVector3;
import behaviour.BehaviourFactory;
import behaviour.expression.ExpressionNumber;
import behaviour.expression.ExpressionVector3;
import behaviour.node.action.Action;

/**
 * 走到目标位置并设置最终朝向
 */
public class ActWalkToPosWithDir extends Action<config.behaviour.node.ActWalkToPosWithDir> {
    private ExpressionVector3<? extends ExpVector3> posExpression;
    private ExpressionNumber<? extends ExpNumber> dirExpression;

    public ActWalkToPosWithDir(config.behaviour.node.ActWalkToPosWithDir atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        posExpression = BehaviourFactory.createExpression(this.getAtomCfg().getPos());
        dirExpression = BehaviourFactory.createExpression(this.getAtomCfg().getDir());
    }

    public ExpressionVector3<? extends ExpVector3> getPosExpression() {
        return posExpression;
    }

    public ExpressionNumber<? extends ExpNumber> getDirExpression() {
        return dirExpression;
    }
}
