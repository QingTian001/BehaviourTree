package config.behaviour;

public enum Conditionenum {
    NULL("NULL", 1),
    STREQUAL("StrEqual", 2),
    OBJEQUAL("ObjEqual", 3),
    NOT("Not", 4),
    AND("And", 5),
    OR("Or", 6),
    COMPARE("Compare", 7),
    SUCCESS("Success", 8),
    FAILURE("Failure", 9);

    private final String name;
    private final int value;

    Conditionenum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private static final java.util.Map<Integer, Conditionenum> map = new java.util.HashMap<>();

    static {
        for(Conditionenum e : Conditionenum.values()) {
            map.put(e.value, e);
        }
    }

    public static Conditionenum get(int value) {
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
