package config.behaviour.node;

// ActWaitTimeMs
public final class ActWaitTimeMs implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTWAITTIMEMS;
    }

    private config.behaviour.expnumber.ExpNumber timeMs;

    private ActWaitTimeMs() {
    }

    public ActWaitTimeMs(config.behaviour.expnumber.ExpNumber timeMs) {
        this.timeMs = timeMs;
    }

    public static ActWaitTimeMs _create(configgen.genjava.ConfigInput input) {
        ActWaitTimeMs self = new ActWaitTimeMs();
        self.timeMs = config.behaviour.expnumber.ExpNumber._create(input);
        return self;
    }

    public config.behaviour.expnumber.ExpNumber getTimeMs() {
        return timeMs;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(timeMs);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActWaitTimeMs))
            return false;
        ActWaitTimeMs o = (ActWaitTimeMs) other;
        return timeMs.equals(o.timeMs);
    }

    @Override
    public String toString() {
        return "ActWaitTimeMs(" + timeMs + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        timeMs._resolve(mgr);
    }

}
