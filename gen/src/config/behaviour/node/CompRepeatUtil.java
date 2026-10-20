package config.behaviour.node;

// CompRepeatUtil 不允许返回running
public final class CompRepeatUtil implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.COMPREPEATUTIL;
    }

    private config.behaviour.condition.Condition cond;
    private config.behaviour.node.Node node;

    private CompRepeatUtil() {
    }

    public CompRepeatUtil(config.behaviour.condition.Condition cond, config.behaviour.node.Node node) {
        this.cond = cond;
        this.node = node;
    }

    public static CompRepeatUtil _create(configgen.genjava.ConfigInput input) {
        CompRepeatUtil self = new CompRepeatUtil();
        self.cond = config.behaviour.condition.Condition._create(input);
        self.node = config.behaviour.node.Node._create(input);
        return self;
    }

    /**
     * 无论node返回Success/failure, 只要cond为true, 就在一帧内, 继续执行node, node不允许返回Running
     */
    public config.behaviour.condition.Condition getCond() {
        return cond;
    }

    public config.behaviour.node.Node getNode() {
        return node;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(cond, node);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CompRepeatUtil))
            return false;
        CompRepeatUtil o = (CompRepeatUtil) other;
        return cond.equals(o.cond) && node.equals(o.node);
    }

    @Override
    public String toString() {
        return "CompRepeatUtil(" + cond + "," + node + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        cond._resolve(mgr);
        node._resolve(mgr);
    }

}
