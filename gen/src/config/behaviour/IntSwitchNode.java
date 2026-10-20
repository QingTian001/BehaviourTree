package config.behaviour;

// IntSwitchNode
public class IntSwitchNode {
    private int caseInt;
    private config.behaviour.node.Node node;
    private String note;

    private IntSwitchNode() {
    }

    public IntSwitchNode(int caseInt, config.behaviour.node.Node node, String note) {
        this.caseInt = caseInt;
        this.node = node;
        this.note = note;
    }

    public static IntSwitchNode _create(configgen.genjava.ConfigInput input) {
        IntSwitchNode self = new IntSwitchNode();
        self.caseInt = input.readInt();
        self.node = config.behaviour.node.Node._create(input);
        self.note = input.readStringInPool();
        return self;
    }

    public int getCaseInt() {
        return caseInt;
    }

    public config.behaviour.node.Node getNode() {
        return node;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(caseInt, node, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof IntSwitchNode))
            return false;
        IntSwitchNode o = (IntSwitchNode) other;
        return caseInt == o.caseInt && node.equals(o.node) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "(" + caseInt + "," + node + "," + note + ")";
    }

    public void _resolve(config.ConfigMgr mgr) {
        node._resolve(mgr);
    }

}
