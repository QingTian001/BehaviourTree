package config.behaviour.expvector3;

// GetPosListenMove
public final class GetPosListenMove implements config.behaviour.expvector3.ExpVector3 {
    @Override
    public config.behaviour.Expvector3enum type() {
        return config.behaviour.Expvector3enum.GETPOSLISTENMOVE;
    }

    private config.behaviour.expobj.ExpObj entity;

    private GetPosListenMove() {
    }

    public GetPosListenMove(config.behaviour.expobj.ExpObj entity) {
        this.entity = entity;
    }

    public static GetPosListenMove _create(configgen.genjava.ConfigInput input) {
        GetPosListenMove self = new GetPosListenMove();
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
        if (!(other instanceof GetPosListenMove))
            return false;
        GetPosListenMove o = (GetPosListenMove) other;
        return entity.equals(o.entity);
    }

    @Override
    public String toString() {
        return "GetPosListenMove(" + entity + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        entity._resolve(mgr);
    }

}
