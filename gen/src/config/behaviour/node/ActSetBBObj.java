package config.behaviour.node;

// ActSetBBObj
public final class ActSetBBObj implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTSETBBOBJ;
    }

    private String bbKey;
    private config.behaviour.expobj.ExpObj value;
    private String note;

    private ActSetBBObj() {
    }

    public ActSetBBObj(String bbKey, config.behaviour.expobj.ExpObj value, String note) {
        this.bbKey = bbKey;
        this.value = value;
        this.note = note;
    }

    public static ActSetBBObj _create(configgen.genjava.ConfigInput input) {
        ActSetBBObj self = new ActSetBBObj();
        self.bbKey = input.readStringInPool();
        self.value = config.behaviour.expobj.ExpObj._create(input);
        self.note = input.readStringInPool();
        return self;
    }

    public String getBbKey() {
        return bbKey;
    }

    public config.behaviour.expobj.ExpObj getValue() {
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
        if (!(other instanceof ActSetBBObj))
            return false;
        ActSetBBObj o = (ActSetBBObj) other;
        return bbKey.equals(o.bbKey) && value.equals(o.value) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "ActSetBBObj(" + bbKey + "," + value + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        value._resolve(mgr);
    }

}
