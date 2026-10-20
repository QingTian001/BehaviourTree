package behaviour.runtime.condition;

import behaviour.runtime.ConditionRuntime;

public interface OrRuntime extends ConditionRuntime {
    ConditionRuntime getLeftCondRuntime();

    ConditionRuntime getRightCondRuntime();
}
