package config.behaviour;

// 来自：behaviour/behaviourEnum.xlsx[expvector3enum]
public enum Expvector3enum {
    CONST("Const", 1),
    GETBBVECTOR3("GetBBVector3", 2),
    GETPOS("GetPos", 3),
    GETPOSLISTENMOVE("GetPosListenMove", 4);

    private final String name;
    private final int value;

    Expvector3enum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static final java.util.Map<Integer, Expvector3enum> map = new java.util.HashMap<>();

    static {
        for(Expvector3enum e : Expvector3enum.values()) {
            map.put(e.value, e);
        }
    }

    public static Expvector3enum get(int value) {
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
