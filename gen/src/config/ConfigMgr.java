package config;

public class ConfigMgr {
    private static volatile ConfigMgr mgr;

    public static ConfigMgr getMgr(){
        return mgr;
    }

    public static void setMgr(ConfigMgr newMgr){
        mgr = newMgr;
    }

    public final java.util.Map<Integer, config.behaviour.Behaviour> behaviour_behaviour_All = new java.util.LinkedHashMap<>();

    public config.behaviour.Behaviour getBehaviourBehaviour(int id) { return behaviour_behaviour_All.get(id); }

    public java.util.Collection<config.behaviour.Behaviour> allBehaviourBehaviour() { return behaviour_behaviour_All.values(); }

    public final java.util.Map<Integer, config.behaviour.Nodeenum_Detail> behaviour_nodeenum_All = new java.util.LinkedHashMap<>();

    public config.behaviour.Nodeenum_Detail getBehaviourNodeenum(int id) { return behaviour_nodeenum_All.get(id); }

    public java.util.Collection<config.behaviour.Nodeenum_Detail> allBehaviourNodeenum() { return behaviour_nodeenum_All.values(); }

    public final java.util.Map<Integer, config.behaviour.Typeenum> behaviour_typeenum_All = new java.util.LinkedHashMap<>();

    public config.behaviour.Typeenum getBehaviourTypeenum(int id) { return behaviour_typeenum_All.get(id); }

    public java.util.Collection<config.behaviour.Typeenum> allBehaviourTypeenum() { return behaviour_typeenum_All.values(); }

}
