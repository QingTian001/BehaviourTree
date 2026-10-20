package behaviour.executor.expression.number;

import behaviour.BehaviourContext;
import behaviour.TypedNumber;
import behaviour.executor.expression.ExpressionExecutor;
import behaviour.expression.number.GetBBNumber;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.ListenerManager;
import behaviour.runtime.expression.number.GetBBNumberRuntime;
import behaviour.task.BehaviourTask;
import util.StringUtil;

public final class GetBBNumberExecutor extends ExpressionExecutor<GetBBNumber, TypedNumber> {
    public GetBBNumberExecutor(GetBBNumber atom) {
        super(atom);
    }

    @Override
    public TypedNumber calculate(BehaviourContext context) {
        Object o = context.getBlackBoard().getValue(getAtom().getAtomCfg().getBbKey());
        if (o == null) {
            throw new RuntimeException(StringUtil.format("should call setBBNumber first. key:{}", getAtom().getAtomCfg().getBbKey()));
        }
        return TypedNumber.of(o);
    }

    @Override
    public TypedNumber calculateAndListenEvent(ExpressionRuntime<TypedNumber> runtime) {
        BehaviourContext context = runtime.getContext();
        GetBBNumberRuntime bbRuntime = (GetBBNumberRuntime) runtime;
        ListenerManager listenerManager = ((BehaviourTask<?>) runtime).getRuntimeListenerManager();
        Object o = listenerManager.getValueAndRegisterBlackBoardListener(
                context.getBlackBoard(), getAtom().getAtomCfg().getBbKey(), bbRuntime.getListener());
        if (o == null) {
            listenerManager.disposeListener(bbRuntime.getListener());
            throw new RuntimeException(StringUtil.format("should call setBBNumber first. key:{}, treeCfgId:{}", getAtom().getAtomCfg().getBbKey(), context.getBehaviourTreeTask().getId()));
        }
        return TypedNumber.of(o);
    }
}
