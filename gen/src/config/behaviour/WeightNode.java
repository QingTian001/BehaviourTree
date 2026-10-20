package config.behaviour;

// WeightNode
public class WeightNode {
    private int weight;
    private config.behaviour.node.Node node;

    private WeightNode() {
    }

    public WeightNode(int weight, config.behaviour.node.Node node) {
        this.weight = weight;
        this.node = node;
    }

    public static WeightNode _create(configgen.genjava.ConfigInput input) {
        WeightNode self = new WeightNode();
        self.weight = input.readInt();
        self.node = config.behaviour.node.Node._create(input);
        return self;
    }

    public int getWeight() {
        return weight;
    }

    public config.behaviour.node.Node getNode() {
        return node;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(weight, node);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof WeightNode))
            return false;
        WeightNode o = (WeightNode) other;
        return weight == o.weight && node.equals(o.node);
    }

    @Override
    public String toString() {
        return "(" + weight + "," + node + ")";
    }

    public void _resolve(config.ConfigMgr mgr) {
        node._resolve(mgr);
    }

}
