package config.behaviour.node;

// ActWalkToPosWithDir
public final class ActWalkToPosWithDir implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTWALKTOPOSWITHDIR;
    }

    private config.behaviour.expvector3.ExpVector3 pos;
    private config.behaviour.expnumber.ExpNumber dir;
    private String note;

    private ActWalkToPosWithDir() {
    }

    public ActWalkToPosWithDir(config.behaviour.expvector3.ExpVector3 pos, config.behaviour.expnumber.ExpNumber dir, String note) {
        this.pos = pos;
        this.dir = dir;
        this.note = note;
    }

    public static ActWalkToPosWithDir _create(configgen.genjava.ConfigInput input) {
        ActWalkToPosWithDir self = new ActWalkToPosWithDir();
        self.pos = config.behaviour.expvector3.ExpVector3._create(input);
        self.dir = config.behaviour.expnumber.ExpNumber._create(input);
        self.note = input.readStringInPool();
        return self;
    }

    public config.behaviour.expvector3.ExpVector3 getPos() {
        return pos;
    }

    public config.behaviour.expnumber.ExpNumber getDir() {
        return dir;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(pos, dir, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActWalkToPosWithDir))
            return false;
        ActWalkToPosWithDir o = (ActWalkToPosWithDir) other;
        return pos.equals(o.pos) && dir.equals(o.dir) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "ActWalkToPosWithDir(" + pos + "," + dir + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        pos._resolve(mgr);
        dir._resolve(mgr);
    }

}
