package config.behaviour;

public enum Expobjenum {
    GETBBOBJ("getBBObj", 1);

    private final String name;
    private final int value;

    Expobjenum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private static final java.util.Map<Integer, Expobjenum> map = new java.util.HashMap<>();

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
