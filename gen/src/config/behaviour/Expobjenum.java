package config.behaviour;

// 来自：behaviour/behaviourEnum.xlsx[expobjenum]
public enum Expobjenum {
    GETBBOBJ("GetBBObj", 1),
    GETENTITYSELF("GetEntitySelf", 2),
    OBJNULL("ObjNull", 3),
    GETBBENTITY("GetBBEntity", 4),
    GETENTITYBYINSTID("GetEntityByInstId", 5),
    CONSTBOOLEAN("ConstBoolean", 6);

    private final String name;
    private final int value;

    Expobjenum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static final java.util.Map<Integer, Expobjenum> map = new java.util.HashMap<>();

    static {
        for(Expobjenum e : Expobjenum.values()) {
            map.put(e.value, e);
        }
    }

    public static Expobjenum get(int value) {
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

}
