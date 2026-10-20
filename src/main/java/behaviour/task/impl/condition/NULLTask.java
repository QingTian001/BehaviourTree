package behaviour.task.impl.condition;
import behaviour.BehaviourContext;
import behaviour.condition.NULL;
import behaviour.task.ConditionTask;
public final class NULLTask extends ConditionTask {
    public NULLTask(NULL atom, ConditionTask parent, BehaviourContext context) { super(atom, parent, context); }
}
