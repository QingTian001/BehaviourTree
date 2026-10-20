package config.behaviour.node;

// ActWaitRandomTimeMs
public final class ActWaitRandomTimeMs implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTWAITRANDOMTIMEMS;
    }

    private int minTimeMs;
    private int maxTimeMs;

    private ActWaitRandomTimeMs() {
    }

    public ActWaitRandomTimeMs(int minTimeMs, int maxTimeMs) {
        this.minTimeMs = minTimeMs;
        this.maxTimeMs = maxTimeMs;
    }

    public static ActWaitRandomTimeMs _create(configgen.genjava.ConfigInput input) {
        ActWaitRandomTimeMs self = new ActWaitRandomTimeMs();
        self.minTimeMs = input.readInt();
        self.maxTimeMs = input.readInt();
        return self;
    }

    public int getMinTimeMs() {
        return minTimeMs;
    }

    public int getMaxTimeMs() {
        return maxTimeMs;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(minTimeMs, maxTimeMs);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActWaitRandomTimeMs))
            return false;
        ActWaitRandomTimeMs o = (ActWaitRandomTimeMs) other;
        return minTimeMs == o.minTimeMs && maxTimeMs == o.maxTimeMs;
    }

    @Override
    public String toString() {
        return "ActWaitRandomTimeMs(" + minTimeMs + "," + maxTimeMs + ")";
    }

}
