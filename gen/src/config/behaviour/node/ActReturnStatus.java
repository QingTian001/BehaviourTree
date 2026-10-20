package config.behaviour.node;

// ActReturnStatus
public final class ActReturnStatus implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTRETURNSTATUS;
    }

    private int status;
    private String note;
    private config.behaviour.Statusenum RefStatus;

    private ActReturnStatus() {
    }

    public ActReturnStatus(int status, String note) {
        this.status = status;
        this.note = note;
    }

    public static ActReturnStatus _create(configgen.genjava.ConfigInput input) {
        ActReturnStatus self = new ActReturnStatus();
        self.status = input.readInt();
        self.note = input.readStringInPool();
        return self;
    }

    public int getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public config.behaviour.Statusenum refStatus() {
        return RefStatus;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(status, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActReturnStatus))
            return false;
        ActReturnStatus o = (ActReturnStatus) other;
        return status == o.status && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "ActReturnStatus(" + status + "," + note + ")";
    }

    public void _resolveDirect(config.ConfigMgr mgr) {
        RefStatus = config.behaviour.Statusenum.get(status);
        configgen.genjava.LoadValueErrs.requireNonNull(RefStatus, "ActReturnStatus.status -> behaviour.statusenum", status);
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        _resolveDirect(mgr);
    }

}
