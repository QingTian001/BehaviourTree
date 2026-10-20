package config.behaviour;

// 来自：behaviour/behaviourEnum.xlsx[expstrenum]
public enum Expstrenum {
    CONST("Const", 1),
    GETBBSTR("GetBBStr", 2),
    TOSTRINGFORNUMBER("ToStringForNumber", 3),
    TOINTSTRINGFORNUMBER("ToIntStringForNumber", 4),
    TOLONGSTRINGFORNUMBER("ToLongStringForNumber", 5),
    TOFLOATSTRINGFORNUMBER("ToFloatStringForNumber", 6),
    TODOUBLESTRINGFORNUMBER("ToDoubleStringForNumber", 7),
    TOSTRINGFORVECTOR3("ToStringForVector3", 8);

    private final String name;
    private final int value;

    Expstrenum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static final java.util.Map<Integer, Expstrenum> map = new java.util.HashMap<>();

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
