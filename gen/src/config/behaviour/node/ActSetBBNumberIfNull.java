package config.behaviour.node;

// ActSetBBNumberIfNull
public final class ActSetBBNumberIfNull implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTSETBBNUMBERIFNULL;
    }

    private String bbKey;
    private config.behaviour.expnumber.ExpNumber value;
    private String note;

    private ActSetBBNumberIfNull() {
    }

    public ActSetBBNumberIfNull(String bbKey, config.behaviour.expnumber.ExpNumber value, String note) {
        this.bbKey = bbKey;
        this.value = value;
        this.note = note;
    }

    public static ActSetBBNumberIfNull _create(configgen.genjava.ConfigInput input) {
        ActSetBBNumberIfNull self = new ActSetBBNumberIfNull();
        self.bbKey = input.readStringInPool();
        self.value = config.behaviour.expnumber.ExpNumber._create(input);
        self.note = input.readStringInPool();
        return self;
    }

    public String getBbKey() {
        return bbKey;
    }

    public config.behaviour.expnumber.ExpNumber getValue() {
        return value;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(bbKey, value, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActSetBBNumberIfNull))
            return false;
        ActSetBBNumberIfNull o = (ActSetBBNumberIfNull) other;
        return bbKey.equals(o.bbKey) && value.equals(o.value) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "ActSetBBNumberIfNull(" + bbKey + "," + value + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        value._resolve(mgr);
    }

}
