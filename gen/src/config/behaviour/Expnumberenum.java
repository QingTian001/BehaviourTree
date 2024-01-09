package config.behaviour;

public enum Expnumberenum {
    CONSTFLOAT("ConstFloat", 1),
    GETBBNUMBER("getBBNumber", 2),
    CONSTLONG("ConstLong", 3),
    CONSTINTEGER("ConstInteger", 4),
    CALCULATE("Calculate", 5);

    private final String name;
    private final int value;

    Expnumberenum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private static final java.util.Map<Integer, Expnumberenum> map = new java.util.HashMap<>();

    static {
        for(Expnumberenum e : Expnumberenum.values()) {
            map.put(e.value, e);
        }
    }

    public static Expnumberenum get(int value) {
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
