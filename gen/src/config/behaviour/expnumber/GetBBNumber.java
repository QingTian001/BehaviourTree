package config.behaviour.expnumber;

public class GetBBNumber implements config.behaviour.ExpNumber {
    @Override
    public config.behaviour.Expnumberenum type() {
        return config.behaviour.Expnumberenum.GETBBNUMBER;
    }

    private String bbKey;

    private GetBBNumber() {
    }

    public GetBBNumber(String bbKey) {
        this.bbKey = bbKey;
    }

    public static GetBBNumber _create(configgen.genjava.ConfigInput input) {
        GetBBNumber self = new GetBBNumber();
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
        if (!(other instanceof GetBBNumber))
            return false;
        GetBBNumber o = (GetBBNumber) other;
        return bbKey.equals(o.bbKey);
    }

    @Override
    public String toString() {
        return "GetBBNumber(" + bbKey + ")";
    }

}
