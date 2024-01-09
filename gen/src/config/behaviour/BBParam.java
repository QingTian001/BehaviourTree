package config.behaviour;

public class BBParam {
    private int bbType;
    private String bbKey;
    private config.behaviour.Typeenum RefBbType;

    private BBParam() {
    }

    public BBParam(int bbType, String bbKey) {
        this.bbType = bbType;
        this.bbKey = bbKey;
    }

    public static BBParam _create(configgen.genjava.ConfigInput input) {
        BBParam self = new BBParam();
        self.bbType = input.readInt();
        self.bbKey = input.readStr();
        return self;
    }

    public int getBbType() {
        return bbType;
    }

    public String getBbKey() {
        return bbKey;
    }

    public config.behaviour.Typeenum refBbType() {
        return RefBbType;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(bbType, bbKey);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof BBParam))
            return false;
        BBParam o = (BBParam) other;
        return bbType == o.bbType && bbKey.equals(o.bbKey);
    }

    @Override
    public String toString() {
        return "(" + bbType + "," + bbKey + ")";
    }

    public void _resolve(config.ConfigMgr mgr) {
        RefBbType = mgr.behaviour_typeenum_All.get(bbType);
        java.util.Objects.requireNonNull(RefBbType);
    }

}
