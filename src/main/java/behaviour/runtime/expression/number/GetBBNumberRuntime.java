package behaviour.runtime.expression.number;

import behaviour.BlackBoard;
import behaviour.TypedNumber;
import behaviour.runtime.ExpressionRuntime;

public interface GetBBNumberRuntime extends ExpressionRuntime<TypedNumber> {
    BlackBoard.IBBListener<?> getListener();
}
