package config.behaviour.expobj;

public class GetBBObj implements config.behaviour.ExpObj {
    @Override
    public config.behaviour.Expobjenum type() {
        return config.behaviour.Expobjenum.GETBBOBJ;
    }

    private String bbKey;

    private GetBBObj() {
    }

    public GetBBObj(String bbKey) {
        this.bbKey = bbKey;
    }

    public static GetBBObj _create(configgen.genjava.ConfigInput input) {
        GetBBObj self = new GetBBObj();
        self.bbKey = input.readStr();
        return self;
    }

    public String getBbKey() {
        return bbKey;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(bbKey);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GetBBObj))
            return false;
        GetBBObj o = (GetBBObj) other;
        return bbKey.equals(o.bbKey);
    }

    @Override
    public String toString() {
        return "GetBBObj(" + bbKey + ")";
    }

}
