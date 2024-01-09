package config.behaviour.expstr;

public class GetBBStr implements config.behaviour.ExpStr {
    @Override
    public config.behaviour.Expstrenum type() {
        return config.behaviour.Expstrenum.GETBBSTR;
    }

    private String bbKey;

    private GetBBStr() {
    }

    public GetBBStr(String bbKey) {
        this.bbKey = bbKey;
    }

    public static GetBBStr _create(configgen.genjava.ConfigInput input) {
        GetBBStr self = new GetBBStr();
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
        if (!(other instanceof GetBBStr))
            return false;
        GetBBStr o = (GetBBStr) other;
        return bbKey.equals(o.bbKey);
    }

    @Override
    public String toString() {
        return "GetBBStr(" + bbKey + ")";
    }

}
