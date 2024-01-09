package behaviour.expression.number;
import behaviour.BehaviourTree;
import behaviour.BlackBoard;
import behaviour.condition.Condition;
import behaviour.expression.ExpressionNumber;


public class GetBBNumber extends ExpressionNumber<config.behaviour.expnumber.GetBBNumber> {
    private final BlackBoard.IBBListener<Double> listener =
            (bbKey, bbValue) -> GetBBNumber.this.getCondition().setDirty();
    @SuppressWarnings("unchecked")
    public GetBBNumber(BehaviourTree behaviourTree, config.behaviour.ExpNumber exprCfg, Condition<? extends config.behaviour.Condition> condition) {
        super(behaviourTree, exprCfg, condition);
    }

    public double calculateExpressionNumber() {
        return getBehaviourTree().getBlackBoard().getValue(getExprCfg().getBbKey(), 0.0);
    }

    public double calculateExpressionNumberAndListenEvent() {
        return getBehaviourTree().getBlackBoard().getValueAndRegisterListener(getExprCfg().getBbKey(), 0.0, listener);
    }

    @Override
    public void reset() {
        super.reset();
        getBehaviourTree().getBlackBoard().unregisterListener(getExprCfg().getBbKey(), listener);
    }
}
