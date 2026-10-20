package behaviour.runtime.decorator;

import behaviour.runtime.node.decorator.StatusDecoratorRuntime;

public interface DecTimedRuntime extends StatusDecoratorRuntime {
    long getStartTimeMs();

    void setStartTimeMs(long startTimeMs);

    double getTimeMs();

    void setTimeMs(double timeMs);
}
