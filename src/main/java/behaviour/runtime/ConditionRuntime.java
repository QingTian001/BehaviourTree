package behaviour.runtime;

public interface ConditionRuntime extends RuntimeSupport {
    boolean calculate();

    boolean calculateAndListenEvent();

    void clearDirty();

    boolean isDirty();

    ConditionRuntime getParentConditionRuntime();
}
