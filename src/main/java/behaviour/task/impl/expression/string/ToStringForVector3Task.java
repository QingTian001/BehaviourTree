package behaviour.task.impl.expression.string;

import behaviour.BehaviourContext;
import behaviour.BehaviourFactory;
import behaviour.runtime.ExpressionRuntime;
import behaviour.runtime.expression.string.ToStringForVector3Runtime;
import behaviour.task.ExpressionStringTask;
import behaviour.task.ConditionTask;
import behaviour.expression.string.ToStringForVector3;
import behaviour.task.ExpressionVector3Task;

/**
 * ToStringForVector3 鐨勮繍琛屾椂浠诲姟瀹炵幇
 * 鐢?migrate_expressions.py 鑷姩鐢熸垚
 */
public class ToStringForVector3Task extends ExpressionStringTask implements ToStringForVector3Runtime {

    // 瀛愯〃杈惧紡浠诲姟
    private ExpressionVector3Task v3Task;

    public ToStringForVector3Task(ToStringForVector3 atom, ConditionTask belongCondition, BehaviourContext context) {
        super(atom, belongCondition, context);
    }

    @Override
    public ToStringForVector3 getAtom() {
        return (ToStringForVector3) super.getAtom();
    }

    public void setV3Task(ExpressionVector3Task v3Task) {
        this.v3Task = v3Task;
    }

    public ExpressionVector3Task getV3Task() {
        return v3Task;
    }

    @Override
    public ExpressionRuntime<math.Vector3> getVector3Runtime() {
        return v3Task;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void init() {
        v3Task = (ExpressionVector3Task) BehaviourFactory.createExpressionRuntime(
                getAtom().getV3Expression(), getCondition(), getContext());
    }

}

