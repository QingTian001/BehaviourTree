package config.behaviour.node;

public class ActSetBBStr implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTSETBBSTR;
    }

    private String bbKey;
    private config.behaviour.ExpStr value;

    private ActSetBBStr() {
    }

    public ActSetBBStr(String bbKey, config.behaviour.ExpStr value) {
        this.bbKey = bbKey;
        this.value = value;
    }

    public static ActSetBBStr _create(configgen.genjava.ConfigInput input) {
        ActSetBBStr self = new ActSetBBStr();
        self.bbKey = input.readStr();
        self.value = config.behaviour.ExpStr._create(input);
        return self;
    }

    public String getBbKey() {
        return bbKey;
    }

    public config.behaviour.ExpStr getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(bbKey, value);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActSetBBStr))
            return false;
        ActSetBBStr o = (ActSetBBStr) other;
        return bbKey.equals(o.bbKey) && value.equals(o.value);
    }

    @Override
    public String toString() {
        return "ActSetBBStr(" + bbKey + "," + value + ")";
    }

}
