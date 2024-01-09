package config.behaviour;

public enum Calculatorenum {
    PLUS("Plus", 1),
    SUB("Sub", 2),
    MULTI("Multi", 3),
    DIVIDE("Divide", 4);

    private final String name;
    private final int value;

    Calculatorenum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private static final java.util.Map<Integer, Calculatorenum> map = new java.util.HashMap<>();

    static {
        for(Calculatorenum e : Calculatorenum.values()) {
            map.put(e.value, e);
        }
    }

    public static Calculatorenum get(int value) {
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
