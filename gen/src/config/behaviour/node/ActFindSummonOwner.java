package config.behaviour.node;

// ActFindSummonOwner
public final class ActFindSummonOwner implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.ACTFINDSUMMONOWNER;
    }

    private float radius;
    private String wbbKey;

    private ActFindSummonOwner() {
    }

    public ActFindSummonOwner(float radius, String wbbKey) {
        this.radius = radius;
        this.wbbKey = wbbKey;
    }

    public static ActFindSummonOwner _create(configgen.genjava.ConfigInput input) {
        ActFindSummonOwner self = new ActFindSummonOwner();
        self.radius = input.readFloat();
        self.wbbKey = input.readStringInPool();
        return self;
    }

    public float getRadius() {
        return radius;
    }

    public String getWbbKey() {
        return wbbKey;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(radius, wbbKey);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ActFindSummonOwner))
            return false;
        ActFindSummonOwner o = (ActFindSummonOwner) other;
        return radius == o.radius && wbbKey.equals(o.wbbKey);
    }

    @Override
    public String toString() {
        return "ActFindSummonOwner(" + radius + "," + wbbKey + ")";
    }

}
