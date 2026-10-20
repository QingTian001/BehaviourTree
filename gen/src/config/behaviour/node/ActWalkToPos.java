package config.behaviour.node;

// ActWalkToPos
public final class ActWalkToPos implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTWALKTOPOS;
    }

    private config.behaviour.expvector3.ExpVector3 pos;
    private String note;

    private ActWalkToPos() {
    }

    public ActWalkToPos(config.behaviour.expvector3.ExpVector3 pos, String note) {
        this.pos = pos;
        this.note = note;
    }

    public static ActWalkToPos _create(configgen.genjava.ConfigInput input) {
        ActWalkToPos self = new ActWalkToPos();
        self.pos = config.behaviour.expvector3.ExpVector3._create(input);
        self.note = input.readStringInPool();
        return self;
    }

    public config.behaviour.expvector3.ExpVector3 getPos() {
        return pos;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(pos, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActWalkToPos))
            return false;
        ActWalkToPos o = (ActWalkToPos) other;
        return pos.equals(o.pos) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "ActWalkToPos(" + pos + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        pos._resolve(mgr);
    }

}
