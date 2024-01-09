package config.behaviour.node;

public class ActSetBBObj implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTSETBBOBJ;
    }

    private String bbKey;
    private config.behaviour.ExpObj value;

    private ActSetBBObj() {
    }

    public ActSetBBObj(String bbKey, config.behaviour.ExpObj value) {
        this.bbKey = bbKey;
        this.value = value;
    }

    public static ActSetBBObj _create(configgen.genjava.ConfigInput input) {
        ActSetBBObj self = new ActSetBBObj();
        self.bbKey = input.readStr();
        self.value = config.behaviour.ExpObj._create(input);
        return self;
    }

    public String getBbKey() {
        return bbKey;
    }

    public config.behaviour.ExpObj getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(bbKey, value);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActSetBBObj))
            return false;
        ActSetBBObj o = (ActSetBBObj) other;
        return bbKey.equals(o.bbKey) && value.equals(o.value);
    }

    @Override
    public String toString() {
        return "ActSetBBObj(" + bbKey + "," + value + ")";
    }

}
