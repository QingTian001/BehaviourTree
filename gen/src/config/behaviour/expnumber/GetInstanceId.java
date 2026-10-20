package config.behaviour.expnumber;

public final class GetInstanceId implements config.behaviour.expnumber.ExpNumber {
    @Override
    public config.behaviour.Expnumberenum type() {
        return config.behaviour.Expnumberenum.GETINSTANCEID;
    }

    private config.behaviour.expobj.ExpObj entity;

    private GetInstanceId() {
    }

    public GetInstanceId(config.behaviour.expobj.ExpObj entity) {
        this.entity = entity;
    }

    public static GetInstanceId _create(configgen.genjava.ConfigInput input) {
        GetInstanceId self = new GetInstanceId();
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
        if (!(other instanceof GetInstanceId))
            return false;
        GetInstanceId o = (GetInstanceId) other;
        return entity.equals(o.entity);
    }

    @Override
    public String toString() {
        return "GetInstanceId(" + entity + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        entity._resolve(mgr);
    }

}
