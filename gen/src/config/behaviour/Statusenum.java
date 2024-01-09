package config.behaviour;

public enum Statusenum {
    BTSUCCESS("BTSuccess", 1),
    BTFAILURE("BTFailure", 2),
    BTRUNNING("BTRunning", 3),
    BTINVALID("BTInvalid", 4);

    private final String name;
    private final int value;

    Statusenum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private static final java.util.Map<Integer, Statusenum> map = new java.util.HashMap<>();

    static {
        for(Statusenum e : Statusenum.values()) {
            map.put(e.value, e);
        }
    }

    public static Statusenum get(int value) {
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
