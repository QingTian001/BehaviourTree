package config.behaviour.node;

// ActSetBBVector3
public final class ActSetBBVector3 implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTSETBBVECTOR3;
    }

    private String bbKey;
    private config.behaviour.expvector3.ExpVector3 value;
    private String note;

    private ActSetBBVector3() {
    }

    public ActSetBBVector3(String bbKey, config.behaviour.expvector3.ExpVector3 value, String note) {
        this.bbKey = bbKey;
        this.value = value;
        this.note = note;
    }

    public static ActSetBBVector3 _create(configgen.genjava.ConfigInput input) {
        ActSetBBVector3 self = new ActSetBBVector3();
        self.bbKey = input.readStringInPool();
        self.value = config.behaviour.expvector3.ExpVector3._create(input);
        self.note = input.readStringInPool();
        return self;
    }

    public String getBbKey() {
        return bbKey;
    }

    public config.behaviour.expvector3.ExpVector3 getValue() {
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
        if (!(other instanceof ActSetBBVector3))
            return false;
        ActSetBBVector3 o = (ActSetBBVector3) other;
        return bbKey.equals(o.bbKey) && value.equals(o.value) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "ActSetBBVector3(" + bbKey + "," + value + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        value._resolve(mgr);
    }

}
