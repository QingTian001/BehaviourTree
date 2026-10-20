package config.behaviour.node;

// 前置条件装饰器：条件失效时中断被装饰节点
public final class DecCondition implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.DECCONDITION;
    }

    private config.behaviour.condition.Condition cond;
    private config.behaviour.node.Node node;
    private String note;

    private DecCondition() {
    }

    public DecCondition(config.behaviour.condition.Condition cond, config.behaviour.node.Node node, String note) {
        this.cond = cond;
        this.node = node;
        this.note = note;
    }

    public static DecCondition _create(configgen.genjava.ConfigInput input) {
        DecCondition self = new DecCondition();
        self.cond = config.behaviour.condition.Condition._create(input);
        self.node = config.behaviour.node.Node._create(input);
        self.note = input.readStringInPool();
        return self;
    }

    public config.behaviour.condition.Condition getCond() {
        return cond;
    }

    public config.behaviour.node.Node getNode() {
        return node;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(cond, node, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DecCondition))
            return false;
        DecCondition o = (DecCondition) other;
        return cond.equals(o.cond) && node.equals(o.node) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "DecCondition(" + cond + "," + node + "," + note + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        cond._resolve(mgr);
        node._resolve(mgr);
    }

}
