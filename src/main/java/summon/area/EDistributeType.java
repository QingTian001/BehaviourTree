package summon.area;

/**
 * Created by zyao on 2025/3/3 16:04
 */
public enum EDistributeType {
    RANDOM(1) ,
    EDGE(2);

    EDistributeType(int value) {
        this.value = value;
    }
    private int value;

    public int getValue() {
        return value;
    }

    public static EDistributeType get(int value) {
        for (EDistributeType type : EDistributeType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new RuntimeException("unexpected EDistributeType:" + value);
    }
}
