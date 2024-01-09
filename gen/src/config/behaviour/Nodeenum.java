package config.behaviour;

public enum Nodeenum {
    SUBTREE("SubTree", 1),
    SUCCESS("Success", 2),
    FAILURE("Failure", 3),
    ENDTREE("EndTree", 4),
    WAITTIMEMS("WaitTimeMs", 5),
    NODENOT("NodeNot", 6),
    LOG("Log", 7),
    LOOP("Loop", 8),
    LOOPUTIL("LoopUtil", 9),
    NODECONDITION("NodeCondition", 10),
    SEQUENCE("Sequence", 11),
    SELECTOR("Selector", 12),
    PARALLEL("Parallel", 13),
    TIMED("Timed", 14),
    ACTDEBUGLOG("ActDebugLog", 15),
    ACTSETBBNUMBER("ActSetBBNumber", 16),
    ACTSETBBOBJ("ActSetBBObj", 17),
    ACTSETBBSTR("ActSetBBStr", 18);

    private final String name;
    private final int value;

    Nodeenum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private static final java.util.Map<Integer, Nodeenum> map = new java.util.HashMap<>();

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
        return ref().getNodeType();
    }

    public config.behaviour.Nodetypeenum refNodeType() {
        return ref().refNodeType();
    }

    public config.behaviour.Nodeenum_Detail ref() {
        return config.behaviour.Nodeenum_Detail.get(value);
    }

}
