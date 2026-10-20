package config.behaviour.expobj;

// ObjNull
public final class ObjNull implements config.behaviour.expobj.ExpObj {
    @Override
    public config.behaviour.Expobjenum type() {
        return config.behaviour.Expobjenum.OBJNULL;
    }


    public ObjNull() {
    }

    public static ObjNull _create(configgen.genjava.ConfigInput input) {
        ObjNull self = new ObjNull();
        return self;
    }

    @Override
    public int hashCode() {
        return ObjNull.class.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ObjNull;
    }

    @Override
    public String toString() {
        return "ObjNull";
    }

}
