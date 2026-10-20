package config.behaviour.expobj;

// GetBBEntity
public final class GetBBEntity implements config.behaviour.expobj.ExpObj {
    @Override
    public config.behaviour.Expobjenum type() {
        return config.behaviour.Expobjenum.GETBBENTITY;
    }

    private String bbKey;

    private GetBBEntity() {
    }

    public GetBBEntity(String bbKey) {
        this.bbKey = bbKey;
    }

    public static GetBBEntity _create(configgen.genjava.ConfigInput input) {
        GetBBEntity self = new GetBBEntity();
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
        if (!(other instanceof GetBBEntity))
            return false;
        GetBBEntity o = (GetBBEntity) other;
        return bbKey.equals(o.bbKey);
    }

    @Override
    public String toString() {
        return "GetBBEntity(" + bbKey + ")";
    }

}
