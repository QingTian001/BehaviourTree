package config.behaviour.node;

// ActFaceToEntity
public final class ActFaceToEntity implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTFACETOENTITY;
    }

    private config.behaviour.expobj.ExpObj entity;

    private ActFaceToEntity() {
    }

    public ActFaceToEntity(config.behaviour.expobj.ExpObj entity) {
        this.entity = entity;
    }

    public static ActFaceToEntity _create(configgen.genjava.ConfigInput input) {
        ActFaceToEntity self = new ActFaceToEntity();
        self.entity = config.behaviour.expobj.ExpObj._create(input);
        return self;
    }

    public config.behaviour.expobj.ExpObj getEntity() {
        return entity;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(entity);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActFaceToEntity))
            return false;
        ActFaceToEntity o = (ActFaceToEntity) other;
        return entity.equals(o.entity);
    }

    @Override
    public String toString() {
        return "ActFaceToEntity(" + entity + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        entity._resolve(mgr);
    }

}
