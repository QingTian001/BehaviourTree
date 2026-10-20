package behaviour.expression.object;

import config.behaviour.expobj.GetBBObj;
import behaviour.expression.ExpressionObject;

/**
 * Get blackboard object expression.
 * Flyweight pattern - logic here, no runtime state.
 */
public class GetBBObject extends ExpressionObject<GetBBObj, Object> {

    public GetBBObject(GetBBObj atomCfg) {
        super(atomCfg);
    }

    @Override
    public void loadFromCfg() {
        // No child expressions to load
    }
}
