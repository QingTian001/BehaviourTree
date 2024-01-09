package config.behaviour.node;

public class ActSetBBNumber implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTSETBBNUMBER;
    }

    private String bbKey;
    private config.behaviour.ExpNumber value;

    private ActSetBBNumber() {
    }

    public ActSetBBNumber(String bbKey, config.behaviour.ExpNumber value) {
        this.bbKey = bbKey;
        this.value = value;
    }

    public static ActSetBBNumber _create(configgen.genjava.ConfigInput input) {
        ActSetBBNumber self = new ActSetBBNumber();
        self.bbKey = input.readStr();
        self.value = config.behaviour.ExpNumber._create(input);
        return self;
    }

    public String getBbKey() {
        return bbKey;
    }

    public config.behaviour.ExpNumber getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(bbKey, value);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActSetBBNumber))
            return false;
        ActSetBBNumber o = (ActSetBBNumber) other;
        return bbKey.equals(o.bbKey) && value.equals(o.value);
    }

    @Override
    public String toString() {
        return "ActSetBBNumber(" + bbKey + "," + value + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        value._resolve(mgr);
    }

}
