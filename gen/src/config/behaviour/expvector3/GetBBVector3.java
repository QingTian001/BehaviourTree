package config.behaviour.expvector3;

// GetBBVector3
public final class GetBBVector3 implements config.behaviour.expvector3.ExpVector3 {
    @Override
    public config.behaviour.Expvector3enum type() {
        return config.behaviour.Expvector3enum.GETBBVECTOR3;
    }

    private String bbKey;

    private GetBBVector3() {
    }

    public GetBBVector3(String bbKey) {
        this.bbKey = bbKey;
    }

    public static GetBBVector3 _create(configgen.genjava.ConfigInput input) {
        GetBBVector3 self = new GetBBVector3();
        self.bbKey = input.readStringInPool();
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
        if (!(other instanceof GetBBVector3))
            return false;
        GetBBVector3 o = (GetBBVector3) other;
        return bbKey.equals(o.bbKey);
    }

    @Override
    public String toString() {
        return "GetBBVector3(" + bbKey + ")";
    }

}
