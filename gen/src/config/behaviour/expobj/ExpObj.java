package config.behaviour.expobj;

public sealed interface ExpObj permits GetEntitySelf, ObjNull, GetBBEntity, GetEntityByInstId, GetBBObj, ConstBoolean {
    config.behaviour.Expobjenum type();

    default void _resolve(config.ConfigMgr mgr) {
    }

    static ExpObj _create(configgen.genjava.ConfigInput input) {
        String tag = input.readStringInPool();
        switch (tag) {
            case "GetEntitySelf":
                return config.behaviour.expobj.GetEntitySelf._create(input);
            case "ObjNull":
                return config.behaviour.expobj.ObjNull._create(input);
            case "GetBBEntity":
                return config.behaviour.expobj.GetBBEntity._create(input);
            case "GetEntityByInstId":
                return config.behaviour.expobj.GetEntityByInstId._create(input);
            case "GetBBObj":
                return config.behaviour.expobj.GetBBObj._create(input);
            case "ConstBoolean":
                return config.behaviour.expobj.ConstBoolean._create(input);
        }
        throw new IllegalArgumentException(tag + " not found");
    }
}
