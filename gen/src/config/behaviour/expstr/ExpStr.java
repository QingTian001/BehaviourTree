package config.behaviour.expstr;

public sealed interface ExpStr permits Const, GetBBStr, ToStringForNumber, ToIntStringForNumber, ToLongStringForNumber, ToFloatStringForNumber, ToDoubleStringForNumber, ToStringForVector3 {
    config.behaviour.Expstrenum type();

    default void _resolve(config.ConfigMgr mgr) {
    }

    static ExpStr _create(configgen.genjava.ConfigInput input) {
        String tag = input.readStringInPool();
        switch (tag) {
            case "Const":
                return config.behaviour.expstr.Const._create(input);
            case "GetBBStr":
                return config.behaviour.expstr.GetBBStr._create(input);
            case "ToStringForNumber":
                return config.behaviour.expstr.ToStringForNumber._create(input);
            case "ToIntStringForNumber":
                return config.behaviour.expstr.ToIntStringForNumber._create(input);
            case "ToLongStringForNumber":
                return config.behaviour.expstr.ToLongStringForNumber._create(input);
            case "ToFloatStringForNumber":
                return config.behaviour.expstr.ToFloatStringForNumber._create(input);
            case "ToDoubleStringForNumber":
                return config.behaviour.expstr.ToDoubleStringForNumber._create(input);
            case "ToStringForVector3":
                return config.behaviour.expstr.ToStringForVector3._create(input);
        }
        throw new IllegalArgumentException(tag + " not found");
    }
}
