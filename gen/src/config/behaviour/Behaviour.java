package config.behaviour;

public class Behaviour {
    private int id;
    private String name;
    private int objType;
    private java.util.List<config.behaviour.BBParam> params;
    private config.behaviour.BBParam returnValue;
    private config.behaviour.Node node;

    private Behaviour() {
    }

    public static Behaviour _create(configgen.genjava.ConfigInput input) {
        Behaviour self = new Behaviour();
        self.id = input.readInt();
        self.name = input.readStr();
        self.objType = input.readInt();
        self.params = new java.util.ArrayList<>();
        for (int c = input.readInt(); c > 0; c--) {
            self.params.add(config.behaviour.BBParam._create(input));
        }
        self.returnValue = config.behaviour.BBParam._create(input);
        self.node = config.behaviour.Node._create(input);
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
    public config.behaviour.Node getNode() {
        return node;
    }

    @Override
    public String toString() {
        return "(" + id + "," + name + "," + objType + "," + params + "," + returnValue + "," + node + ")";
    }

    public void _resolve(config.ConfigMgr mgr) {
        params.forEach( e -> {
            e._resolve(mgr);
        });
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
            for (int c = input.readInt(); c > 0; c--) {
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
