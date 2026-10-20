package behaviour.runtime.expression.string;

import behaviour.BlackBoard;
import behaviour.runtime.ExpressionRuntime;

public interface GetBBStrRuntime extends ExpressionRuntime<String> {
    BlackBoard.IBBListener<String> getListener();
}
