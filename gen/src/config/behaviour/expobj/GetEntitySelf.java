package config.behaviour.expobj;

// GetEntitySelf
public final class GetEntitySelf implements config.behaviour.expobj.ExpObj {
    @Override
    public config.behaviour.Expobjenum type() {
        return config.behaviour.Expobjenum.GETENTITYSELF;
    }


    public GetEntitySelf() {
    }

    public static GetEntitySelf _create(configgen.genjava.ConfigInput input) {
        GetEntitySelf self = new GetEntitySelf();
        return self;
    }

    @Override
    public int hashCode() {
        return GetEntitySelf.class.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof GetEntitySelf;
    }

    @Override
    public String toString() {
        return "GetEntitySelf";
    }

}
