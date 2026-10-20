package behaviour.executor.expression.number;

import behaviour.BehaviourContext;
import behaviour.TypedNumber;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.number.GetCurrentTimeMills;
import behaviour.runtime.ExpressionRuntime;

public final class GetCurrentTimeMillsExecutor extends ExpressionExecutor<GetCurrentTimeMills, TypedNumber> {
    public GetCurrentTimeMillsExecutor(GetCurrentTimeMills atom) {
        super(atom);
    }

    @Override
    public TypedNumber calculate(BehaviourContext context) {
        return TypedNumber.of(context.getHost().getNowMills());
    }

    @Override
    public TypedNumber calculateAndListenEvent(ExpressionRuntime<TypedNumber> runtime) {
        return calculate(runtime.getContext());
    }
}
