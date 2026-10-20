package config.behaviour.expobj;

// GetEntityByInstId
public final class GetEntityByInstId implements config.behaviour.expobj.ExpObj {
    @Override
    public config.behaviour.Expobjenum type() {
        return config.behaviour.Expobjenum.GETENTITYBYINSTID;
    }

    private config.behaviour.expnumber.ExpNumber instId;

    private GetEntityByInstId() {
    }

    public GetEntityByInstId(config.behaviour.expnumber.ExpNumber instId) {
        this.instId = instId;
    }

    public static GetEntityByInstId _create(configgen.genjava.ConfigInput input) {
        GetEntityByInstId self = new GetEntityByInstId();
        self.instId = config.behaviour.expnumber.ExpNumber._create(input);
        return self;
    }

    public config.behaviour.expnumber.ExpNumber getInstId() {
        return instId;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(instId);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GetEntityByInstId))
            return false;
        GetEntityByInstId o = (GetEntityByInstId) other;
        return instId.equals(o.instId);
    }

    @Override
    public String toString() {
        return "GetEntityByInstId(" + instId + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        instId._resolve(mgr);
    }

}
