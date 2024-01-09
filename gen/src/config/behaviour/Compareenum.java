package config.behaviour;

public enum Compareenum {
    EQUAL("Equal", 1),
    LESS("Less", 2),
    GREATER("Greater", 3),
    LESSEQUAL("LessEqual", 4),
    GREATEREQUAL("GreaterEqual", 5);

    private final String name;
    private final int value;

    Compareenum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private static final java.util.Map<Integer, Compareenum> map = new java.util.HashMap<>();

    static {
        for(Compareenum e : Compareenum.values()) {
            map.put(e.value, e);
        }
    }

    public static Compareenum get(int value) {
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
