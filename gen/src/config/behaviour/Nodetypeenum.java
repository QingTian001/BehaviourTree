package config.behaviour;

public enum Nodetypeenum {
    ACTION("Action", 1),
    COMPOSITE("Composite", 2),
    DECORATOR("Decorator", 3),
    CONDITION("Condition", 4);

    private final String name;
    private final int value;

    Nodetypeenum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private static final java.util.Map<Integer, Nodetypeenum> map = new java.util.HashMap<>();

    static {
        for(Nodetypeenum e : Nodetypeenum.values()) {
            map.put(e.value, e);
        }
    }

    public static Nodetypeenum get(int value) {
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
