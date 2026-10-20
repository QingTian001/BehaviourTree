package behaviour.runtime.condition;

import behaviour.runtime.ConditionRuntime;

public interface AndRuntime extends ConditionRuntime {
    ConditionRuntime getLeftCondRuntime();

    ConditionRuntime getRightCondRuntime();
}
