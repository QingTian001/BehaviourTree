package config.behaviour.node;

// ActSetBBStr
public final class ActSetBBStr implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTSETBBSTR;
    }

    private String bbKey;
    private config.behaviour.expstr.ExpStr value;
    private String note;

    private ActSetBBStr() {
    }

    public ActSetBBStr(String bbKey, config.behaviour.expstr.ExpStr value, String note) {
        this.bbKey = bbKey;
        this.value = value;
        this.note = note;
    }

    public static ActSetBBStr _create(configgen.genjava.ConfigInput input) {
        ActSetBBStr self = new ActSetBBStr();
        self.bbKey = input.readStringInPool();
        self.value = config.behaviour.expstr.ExpStr._create(input);
        self.note = input.readStringInPool();
        return self;
    }

    public String getBbKey() {
        return bbKey;
    }

    public config.behaviour.expstr.ExpStr getValue() {
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
        if (!(other instanceof ActSetBBStr))
            return false;
        ActSetBBStr o = (ActSetBBStr) other;
        return bbKey.equals(o.bbKey) && value.equals(o.value) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "ActSetBBStr(" + bbKey + "," + value + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        value._resolve(mgr);
    }

}
