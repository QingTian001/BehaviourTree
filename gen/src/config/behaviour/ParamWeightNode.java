package config.behaviour;

// ParamWeightNode
public class ParamWeightNode {
    private config.behaviour.expnumber.ExpNumber weight;
    private config.behaviour.node.Node node;

    private ParamWeightNode() {
    }

    public ParamWeightNode(config.behaviour.expnumber.ExpNumber weight, config.behaviour.node.Node node) {
        this.weight = weight;
        this.node = node;
    }

    public static ParamWeightNode _create(configgen.genjava.ConfigInput input) {
        ParamWeightNode self = new ParamWeightNode();
        self.weight = config.behaviour.expnumber.ExpNumber._create(input);
        self.node = config.behaviour.node.Node._create(input);
        return self;
    }

    public config.behaviour.expnumber.ExpNumber getWeight() {
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
        if (!(other instanceof ParamWeightNode))
            return false;
        ParamWeightNode o = (ParamWeightNode) other;
        return weight.equals(o.weight) && node.equals(o.node);
    }

    @Override
    public String toString() {
        return "(" + weight + "," + node + ")";
    }

    public void _resolve(config.ConfigMgr mgr) {
        weight._resolve(mgr);
        node._resolve(mgr);
    }

}
