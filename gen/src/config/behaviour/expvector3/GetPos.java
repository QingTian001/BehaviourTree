package config.behaviour.expvector3;

// GetPos
public final class GetPos implements config.behaviour.expvector3.ExpVector3 {
    @Override
    public config.behaviour.Expvector3enum type() {
        return config.behaviour.Expvector3enum.GETPOS;
    }

    private config.behaviour.expobj.ExpObj entity;

    private GetPos() {
    }

    public GetPos(config.behaviour.expobj.ExpObj entity) {
        this.entity = entity;
    }

    public static GetPos _create(configgen.genjava.ConfigInput input) {
        GetPos self = new GetPos();
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
        if (!(other instanceof GetPos))
            return false;
        GetPos o = (GetPos) other;
        return entity.equals(o.entity);
    }

    @Override
    public String toString() {
        return "GetPos(" + entity + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        entity._resolve(mgr);
    }

}
