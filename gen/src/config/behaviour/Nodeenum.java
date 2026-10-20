package config.behaviour;

// 来自：behaviour/behaviourEnum.xlsx[nodeenum]
public enum Nodeenum {
    SUBTREE("SubTree", 1),
    DECSUCCESS("DecSuccess", 2),
    DECFAILURE("DecFailure", 3),
    ACTRETURNSTATUS("ActReturnStatus", 4),
    ACTWAITTIMEMS("ActWaitTimeMs", 5),
    DECNOT("DecNot", 6),
    DECLOG("DecLog", 7),
    DECLOOP("DecLoop", 8),
    DECLOOPUTIL("DecLoopUtil", 9),
    ACTCONDITION("ActCondition", 10),
    COMPSEQUENCE("CompSequence", 11),
    COMPSELECTOR("CompSelector", 12),
    COMPPARALLEL("CompParallel", 13),
    DECTIMED("DecTimed", 14),
    ACTDEBUGMSG("ActDebugMsg", 15),
    ACTSETBBNUMBER("ActSetBBNumber", 16),
    ACTSETBBOBJ("ActSetBBObj", 17),
    ACTSETBBSTR("ActSetBBStr", 18),
    ACTWAITRANDOMTIMEMS("ActWaitRandomTimeMs", 19),
    DECCONDITION("DecCondition", 20),
    COMPINTSWITCHCASE("CompIntSwitchCase", 21),
    COMPWEIGHTSELECTOR("CompWeightSelector", 22),
    COMPCONDWEIGHTSELECTOR("CompCondWeightSelector", 23),
    COMPPARAMWEIGHTSELECTOR("CompParamWeightSelector", 24),
    COMPREPEATUTIL("CompRepeatUtil", 25),
    ACTSETBBNUMBERIFNULL("ActSetBBNumberIfNull", 26),
    ACTSETBBVECTOR3("ActSetBBVector3", 27),
    ACTWALKTOPOS("ActWalkToPos", 28),
    ACTWALKTOPOSWITHDIR("ActWalkToPosWithDir", 29),
    ACTMOVESTOP("ActMoveStop", 30),
    ACTCHANGEDIR("ActChangeDir", 31),
    ACTFACETOENTITY("ActFaceToEntity", 32),
    ACTINTERACTIONENTER("ActInteractionEnter", 33),
    ACTINTERACTIONEXIT("ActInteractionExit", 34),
    ACTFINDNONEPLAYERENTITY("ActFindNonePlayerEntity", 35),
    ACTFINDSUMMONOWNER("ActFindSummonOwner", 36);

    private final String name;
    private final int value;
    private volatile config.behaviour.Nodeenum_Detail ref;

    Nodeenum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static final java.util.Map<Integer, Nodeenum> map = new java.util.HashMap<>();

    static {
        for(Nodeenum e : Nodeenum.values()) {
            map.put(e.value, e);
        }
    }

    public static Nodeenum get(int value) {
        return map.get(value);
    }

    public int getId() {
        return value;
    }

    /**
     * 枚举名
     */
    public String getEname() {
        return name;
    }

    /**
     * 节点类型(编辑器显示使用,不参与逻辑)
     */
    public int getNodeType() {
        return ref.getNodeType();
    }

    public config.behaviour.Nodetypeenum refNodeType() {
        return ref.refNodeType();
    }

    public config.behaviour.Nodeenum_Detail ref() {
        return ref;
    }

    void setRef(config.ConfigMgr mgr) {
        ref = mgr.behaviour_nodeenum_All.get(value);
        configgen.genjava.LoadValueErrs.requireNonNull(ref, "behaviour.nodeenum.setRef", value);
    }

    public static void setAllRefs(config.ConfigMgr mgr) {
        for(Nodeenum e : Nodeenum.values()) {
            e.setRef(mgr);
        }
    }
}
