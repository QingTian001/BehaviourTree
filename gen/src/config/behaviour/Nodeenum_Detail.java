package config.behaviour;

// 来自：behaviour/behaviourEnum.xlsx[nodeenum]
public class Nodeenum_Detail {
    private int id;
    private String ename;
    private int nodeType;
    private config.behaviour.Nodetypeenum RefNodeType;

    private Nodeenum_Detail() {
    }

    public static Nodeenum_Detail _create(configgen.genjava.ConfigInput input) {
        Nodeenum_Detail self = new Nodeenum_Detail();
        self.id = input.readInt();
        self.ename = input.readStringInPool();
        self.nodeType = input.readInt();
        return self;
    }

    public int getId() {
        return id;
    }

    /**
     * 枚举名
     */
    public String getEname() {
        return ename;
    }

    /**
     * 节点类型(编辑器显示使用,不参与逻辑)
     */
    public int getNodeType() {
        return nodeType;
    }

    public config.behaviour.Nodetypeenum refNodeType() {
        return RefNodeType;
    }

    @Override
    public String toString() {
        return "(" + id + "," + ename + "," + nodeType + ")";
    }

    public void _resolveDirect(config.ConfigMgr mgr) {
        RefNodeType = config.behaviour.Nodetypeenum.get(nodeType);
        configgen.genjava.LoadValueErrs.requireNonNull(RefNodeType, "behaviour.nodeenum.nodeType -> behaviour.nodetypeenum", nodeType);
    }

    public void _resolve(config.ConfigMgr mgr) {
        _resolveDirect(mgr);
    }

    public static Nodeenum_Detail get(int id) {
        config.ConfigMgr mgr = config.ConfigMgr.getMgr();
        return mgr.getBehaviourNodeenum(id);
    }

    public static java.util.Collection<Nodeenum_Detail> all() {
        config.ConfigMgr mgr = config.ConfigMgr.getMgr();
        return mgr.allBehaviourNodeenum();
    }
    public static class _ConfigLoader implements config.ConfigLoader {

        @Override
        public void createAll(config.ConfigMgr mgr, configgen.genjava.ConfigInput input) {
            int c = input.readInt();
            mgr.behaviour_nodeenum_All = new java.util.LinkedHashMap<>(c);
            for (; c > 0; c--) {
                Nodeenum_Detail self = Nodeenum_Detail._create(input);
                mgr.behaviour_nodeenum_All.put(self.id, self);
            }
        }

        @Override
        public void resolveAll(config.ConfigMgr mgr) {
            for (Nodeenum_Detail e : mgr.behaviour_nodeenum_All.values()) {
                e._resolve(mgr);
            }
        }

    }

}
