package config.behaviour.node;

// ActChangeDir
public final class ActChangeDir implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTCHANGEDIR;
    }

    private config.behaviour.expobj.ExpObj entity;
    private config.behaviour.expnumber.ExpNumber dir;

    private ActChangeDir() {
    }

    public ActChangeDir(config.behaviour.expobj.ExpObj entity, config.behaviour.expnumber.ExpNumber dir) {
        this.entity = entity;
        this.dir = dir;
    }

    public static ActChangeDir _create(configgen.genjava.ConfigInput input) {
        ActChangeDir self = new ActChangeDir();
        self.entity = config.behaviour.expobj.ExpObj._create(input);
        self.dir = config.behaviour.expnumber.ExpNumber._create(input);
        return self;
    }

    public config.behaviour.expobj.ExpObj getEntity() {
        return entity;
    }

    public config.behaviour.expnumber.ExpNumber getDir() {
        return dir;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(entity, dir);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActChangeDir))
            return false;
        ActChangeDir o = (ActChangeDir) other;
        return entity.equals(o.entity) && dir.equals(o.dir);
    }

    @Override
    public String toString() {
        return "ActChangeDir(" + entity + "," + dir + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        entity._resolve(mgr);
        dir._resolve(mgr);
    }

}
