package config.behaviour.node;

public class NodeCondition implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.NODECONDITION;
    }

    private config.behaviour.Condition cond;

    private NodeCondition() {
    }

    public NodeCondition(config.behaviour.Condition cond) {
        this.cond = cond;
    }

    public static NodeCondition _create(configgen.genjava.ConfigInput input) {
        NodeCondition self = new NodeCondition();
        self.cond = config.behaviour.Condition._create(input);
        return self;
    }

    public config.behaviour.Condition getCond() {
        return cond;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(cond);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof NodeCondition))
            return false;
        NodeCondition o = (NodeCondition) other;
        return cond.equals(o.cond);
    }

    @Override
    public String toString() {
        return "NodeCondition(" + cond + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        cond._resolve(mgr);
    }

}
