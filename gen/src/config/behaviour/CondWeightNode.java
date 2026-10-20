package config.behaviour;

// CondWeightNode
public class CondWeightNode {
    private config.behaviour.condition.Condition cond;
    private int weight;
    private config.behaviour.node.Node node;

    private CondWeightNode() {
    }

    public CondWeightNode(config.behaviour.condition.Condition cond, int weight, config.behaviour.node.Node node) {
        this.cond = cond;
        this.weight = weight;
        this.node = node;
    }

    public static CondWeightNode _create(configgen.genjava.ConfigInput input) {
        CondWeightNode self = new CondWeightNode();
        self.cond = config.behaviour.condition.Condition._create(input);
        self.weight = input.readInt();
        self.node = config.behaviour.node.Node._create(input);
        return self;
    }

    public config.behaviour.condition.Condition getCond() {
        return cond;
    }

    public int getWeight() {
        return weight;
    }

    public config.behaviour.node.Node getNode() {
        return node;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(cond, weight, node);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CondWeightNode))
            return false;
        CondWeightNode o = (CondWeightNode) other;
        return cond.equals(o.cond) && weight == o.weight && node.equals(o.node);
    }

    @Override
    public String toString() {
        return "(" + cond + "," + weight + "," + node + ")";
    }

    public void _resolve(config.ConfigMgr mgr) {
        cond._resolve(mgr);
        node._resolve(mgr);
    }

}
