package behaviour.runtime.action.actions.move;

public interface ActWalkToPosWithDirRuntime extends ActWalkToPosRuntime {
    float getFinalDir();

    void setFinalDir(float finalDir);
}
