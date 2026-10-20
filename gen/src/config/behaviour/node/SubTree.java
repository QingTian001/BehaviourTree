package config.behaviour.node;

// SubTree
public final class SubTree implements config.behaviour.node.Node {
    @Override
    public config.behaviour.Nodeenum type() {
        return config.behaviour.Nodeenum.SUBTREE;
    }

    private int subTreeId;
    private java.util.List<String> paramBBKeys;
    private String returnValueBBKey;
    private String note;
    private config.behaviour.Behaviour RefSubTreeId;

    private SubTree() {
    }

    public SubTree(int subTreeId, java.util.List<String> paramBBKeys, String returnValueBBKey, String note) {
        this.subTreeId = subTreeId;
        this.paramBBKeys = paramBBKeys;
        this.returnValueBBKey = returnValueBBKey;
        this.note = note;
    }

    public static SubTree _create(configgen.genjava.ConfigInput input) {
        SubTree self = new SubTree();
        self.subTreeId = input.readInt();
        {
            int c = input.readInt();
            if (c == 0) {
                self.paramBBKeys = java.util.Collections.emptyList();
            } else {
                self.paramBBKeys = new java.util.ArrayList<>(c);
                for (; c > 0; c--) {
                    self.paramBBKeys.add(input.readStringInPool());
                }
            }
        }
        self.returnValueBBKey = input.readStringInPool();
        self.note = input.readStringInPool();
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

    public String getNote() {
        return note;
    }

    public config.behaviour.Behaviour refSubTreeId() {
        return RefSubTreeId;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(subTreeId, paramBBKeys, returnValueBBKey, note);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SubTree))
            return false;
        SubTree o = (SubTree) other;
        return subTreeId == o.subTreeId && paramBBKeys.equals(o.paramBBKeys) && returnValueBBKey.equals(o.returnValueBBKey) && note.equals(o.note);
    }

    @Override
    public String toString() {
        return "SubTree(" + subTreeId + "," + paramBBKeys + "," + returnValueBBKey + "," + note + ")";
    }

    public void _resolveDirect(config.ConfigMgr mgr) {
        RefSubTreeId = mgr.behaviour_behaviour_All.get(subTreeId);
        configgen.genjava.LoadValueErrs.requireNonNull(RefSubTreeId, "SubTree.subTreeId -> behaviour.behaviour", subTreeId);
    }

    @Override
    public void _resolve(config.ConfigMgr mgr) {
        _resolveDirect(mgr);
    }

}
