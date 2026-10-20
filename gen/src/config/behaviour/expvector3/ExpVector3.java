package config.behaviour.expvector3;

public sealed interface ExpVector3 permits Const, GetBBVector3, GetPos, GetPosListenMove {
    config.behaviour.Expvector3enum type();

    default void _resolve(config.ConfigMgr mgr) {
    }

    static ExpVector3 _create(configgen.genjava.ConfigInput input) {
        String tag = input.readStringInPool();
        switch (tag) {
            case "Const":
                return config.behaviour.expvector3.Const._create(input);
            case "GetBBVector3":
                return config.behaviour.expvector3.GetBBVector3._create(input);
            case "GetPos":
                return config.behaviour.expvector3.GetPos._create(input);
            case "GetPosListenMove":
                return config.behaviour.expvector3.GetPosListenMove._create(input);
        }
        throw new IllegalArgumentException(tag + " not found");
    }
}
