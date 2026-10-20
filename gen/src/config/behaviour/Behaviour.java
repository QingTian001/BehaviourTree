package config.behaviour;

public class Behaviour {
    private int id;
    private String name;
    private int objType;
    private boolean debug;
    private java.util.List<config.behaviour.BBParam> params;
    private config.behaviour.BBParam returnValue;
    private config.behaviour.node.Node node;
    private String note;

    private Behaviour() {
    }

    public static Behaviour _create(configgen.genjava.ConfigInput input) {
        Behaviour self = new Behaviour();
        self.id = input.readInt();
        self.name = input.readStringInPool();
        self.objType = input.readInt();
        self.debug = input.readBool();
        {
            int c = input.readInt();
            if (c == 0) {
                self.params = java.util.Collections.emptyList();
            } else {
                self.params = new java.util.ArrayList<>(c);
                for (; c > 0; c--) {
                    self.params.add(config.behaviour.BBParam._create(input));
                }
            }
        }
        self.returnValue = config.behaviour.BBParam._create(input);
        self.node = config.behaviour.node.Node._create(input);
        self.note = input.readStringInPool();
        return self;
    }

    public int getId() {
        return id;
    }

    /**
     * 行为树名
     */
    public String getName() {
        return name;
    }

    public int getObjType() {
        return objType;
    }

    public boolean getDebug() {
        return debug;
    }

    /**
     * 参数
     */
    public java.util.List<config.behaviour.BBParam> getParams() {
        return params;
    }

    /**
     * 返回值
     */
    public config.behaviour.BBParam getReturnValue() {
        return returnValue;
    }

    /**
     * 节点
     */
    public config.behaviour.node.Node getNode() {
        return node;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "(" + id + "," + name + "," + objType + "," + debug + "," + params + "," + returnValue + "," + node + "," + note + ")";
    }

    public void _resolve(config.ConfigMgr mgr) {
        for (config.behaviour.BBParam e : params) {
            e._resolve(mgr);
        }
        returnValue._resolve(mgr);
        node._resolve(mgr);
    }

    public static Behaviour get(int id) {
        config.ConfigMgr mgr = config.ConfigMgr.getMgr();
        return mgr.getBehaviourBehaviour(id);
    }

    public static java.util.Collection<Behaviour> all() {
        config.ConfigMgr mgr = config.ConfigMgr.getMgr();
        return mgr.allBehaviourBehaviour();
    }
    public static class _ConfigLoader implements config.ConfigLoader {

        @Override
        public void createAll(config.ConfigMgr mgr, configgen.genjava.ConfigInput input) {
            int c = input.readInt();
            mgr.behaviour_behaviour_All = new java.util.LinkedHashMap<>(c);
            for (; c > 0; c--) {
                Behaviour self = Behaviour._create(input);
                mgr.behaviour_behaviour_All.put(self.id, self);
            }
        }

        @Override
        public void resolveAll(config.ConfigMgr mgr) {
            for (Behaviour e : mgr.behaviour_behaviour_All.values()) {
                e._resolve(mgr);
            }
        }

    }

}
