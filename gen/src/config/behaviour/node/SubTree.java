package config.behaviour.node;

public class SubTree implements config.behaviour.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.SUBTREE;
    }

    private int subTreeId;
    private java.util.List<String> paramBBKeys;
    private String returnValueBBKey;
    private config.behaviour.Behaviour RefSubTreeId;

    private SubTree() {
    }

    public SubTree(int subTreeId, java.util.List<String> paramBBKeys, String returnValueBBKey) {
        this.subTreeId = subTreeId;
        this.paramBBKeys = paramBBKeys;
        this.returnValueBBKey = returnValueBBKey;
    }

    public static SubTree _create(configgen.genjava.ConfigInput input) {
        SubTree self = new SubTree();
        self.subTreeId = input.readInt();
        self.paramBBKeys = new java.util.ArrayList<>();
        for (int c = input.readInt(); c > 0; c--) {
            self.paramBBKeys.add(input.readStr());
        }
        self.returnValueBBKey = input.readStr();
        return self;
    }

    public int getSubTreeId() {
        return subTreeId;
    }

    public java.util.List<String> getParamBBKeys() {
        return paramBBKeys;
    }

    public String getReturnValueBBKey() {
        return returnValueBBKey;
    }

    public config.behaviour.Behaviour refSubTreeId() {
        return RefSubTreeId;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(subTreeId, paramBBKeys, returnValueBBKey);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SubTree))
            return false;
        SubTree o = (SubTree) other;
        return subTreeId == o.subTreeId && paramBBKeys.equals(o.paramBBKeys) && returnValueBBKey.equals(o.returnValueBBKey);
    }

    @Override
    public String toString() {
        return "SubTree(" + subTreeId + "," + paramBBKeys + "," + returnValueBBKey + ")";
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        RefSubTreeId = mgr.behaviour_behaviour_All.get(subTreeId);
        java.util.Objects.requireNonNull(RefSubTreeId);
    }

}
