package config.behaviour;

public enum Expstrenum {
    CONST("Const", 1),
    GETBBSTR("getBBStr", 2);

    private final String name;
    private final int value;

    Expstrenum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private static final java.util.Map<Integer, Expstrenum> map = new java.util.HashMap<>();

    static {
        for(Expstrenum e : Expstrenum.values()) {
            map.put(e.value, e);
        }
    }

    public static Expstrenum get(int value) {
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
