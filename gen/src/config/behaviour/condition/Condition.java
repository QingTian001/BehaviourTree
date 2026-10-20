package config.behaviour.condition;

public sealed interface Condition permits NULL, Success, Failure, Compare, StrEqual, ObjEqual, Not, And, Or, IsEntityDestroyed, CheckIsInInteraction {
    config.behaviour.Conditionenum type();

    default void _resolve(config.ConfigMgr mgr) {
    }

    static Condition _create(configgen.genjava.ConfigInput input) {
        String tag = input.readStringInPool();
        switch (tag) {
            case "NULL":
                return config.behaviour.condition.NULL._create(input);
            case "Success":
                return config.behaviour.condition.Success._create(input);
            case "Failure":
                return config.behaviour.condition.Failure._create(input);
            case "Compare":
                return config.behaviour.condition.Compare._create(input);
            case "StrEqual":
                return config.behaviour.condition.StrEqual._create(input);
            case "ObjEqual":
                return config.behaviour.condition.ObjEqual._create(input);
            case "Not":
                return config.behaviour.condition.Not._create(input);
            case "And":
                return config.behaviour.condition.And._create(input);
            case "Or":
                return config.behaviour.condition.Or._create(input);
            case "IsEntityDestroyed":
                return config.behaviour.condition.IsEntityDestroyed._create(input);
            case "checkIsInInteraction":
                return config.behaviour.condition.CheckIsInInteraction._create(input);
        }
        throw new IllegalArgumentException(tag + " not found");
    }
}
