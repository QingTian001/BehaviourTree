package config.behaviour;

// 来自：behaviour/behaviourEnum.xlsx[expnumberenum]
public enum Expnumberenum {
    CONSTFLOAT("ConstFloat", 1),
    GETBBNUMBER("getBBNumber", 2),
    CONSTLONG("ConstLong", 3),
    CONSTINTEGER("ConstInteger", 4),
    CALCULATE("Calculate", 5),
    RANDINTEGER("RandInteger", 6),
    MAX("Max", 7),
    MIN("Min", 8),
    GETINSTANCEID("GetInstanceId", 9),
    GETCURRENTTIMEMILLS("GetCurrentTimeMills", 10),
    CALCDIST("CalcDist", 11);

    private final String name;
    private final int value;

    Expnumberenum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static final java.util.Map<Integer, Expnumberenum> map = new java.util.HashMap<>();

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
