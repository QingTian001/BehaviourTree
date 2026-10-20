package behaviour.runtime.expression.object;

import behaviour.BlackBoard;
import behaviour.runtime.ExpressionRuntime;

public interface GetBBObjectRuntime extends ExpressionRuntime<Object> {
    BlackBoard.IBBListener<Object> getListener();
}
