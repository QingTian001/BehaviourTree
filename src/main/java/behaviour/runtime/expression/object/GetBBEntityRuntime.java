package behaviour.runtime.expression.object;

import behaviour.GEntity;
import behaviour.BlackBoard;
import behaviour.runtime.ExpressionRuntime;

public interface GetBBEntityRuntime extends ExpressionRuntime<GEntity> {
    BlackBoard.IBBListener<GEntity> getListener();
}
