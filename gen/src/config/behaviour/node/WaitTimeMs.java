package config.behaviour.node;

public class WaitTimeMs implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.WAITTIMEMS;
    }

    private config.behaviour.ExpNumber timeMs;

    private WaitTimeMs() {
    }

    public WaitTimeMs(config.behaviour.ExpNumber timeMs) {
        this.timeMs = timeMs;
    }

    public static WaitTimeMs _create(configgen.genjava.ConfigInput input) {
        WaitTimeMs self = new WaitTimeMs();
        self.timeMs = config.behaviour.ExpNumber._create(input);
        return self;
    }

    public config.behaviour.ExpNumber getTimeMs() {
        return timeMs;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(timeMs);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof WaitTimeMs))
            return false;
        WaitTimeMs o = (WaitTimeMs) other;
        return timeMs.equals(o.timeMs);
    }

    @Override
    public String toString() {
        return "WaitTimeMs(" + timeMs + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        timeMs._resolve(mgr);
    }

}
