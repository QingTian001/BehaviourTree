package behaviour.expression.number;
import behaviour.BehaviourTree;
import behaviour.BlackBoard;
import behaviour.condition.Condition;
import behaviour.expression.ExpressionNumber;

/**
 * Created by zyao on 2023/12/28 14:46
 */
public class GetBBNumber extends ExpressionNumber<config.behaviour.expnumber.GetBBNumber> {
    private final BlackBoard.IBBListener<Double> listener =
            (bbKey, bbValue) -> GetBBNumber.this.getCondition().setDirty();
    @SuppressWarnings("unchecked")
    public GetBBNumber(BehaviourTree behaviourTree, config.behaviour.expnumber.GetBBNumber exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        super(behaviourTree, exprCfg, condition);
    }

    protected Double internalCalculateExpression() {
        return getBehaviourTree().getBlackBoard().getValue(getExprCfg().getBbKey(), 0.0);
    }

    protected Double internalCalculateExpressionAndListenEvent() {
        return getBehaviourTree().getBlackBoard().getValueAndRegisterListener(getExprCfg().getBbKey(), 0.0, listener);
    }

    @Override
    public void reset() {
        super.reset();
        getBehaviourTree().getBlackBoard().unregisterListener(getExprCfg().getBbKey(), listener);
    }
}
