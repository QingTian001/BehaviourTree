package summon.param;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyao on 2025/9/9 10:56
 */
public abstract class SummonEntityParam {
    private boolean aoiPublic;
    private long leaveMapTimeStampMills = 0;
    private int overrideSummonCount;

    @SuppressWarnings("unchecked")
    public <T extends SummonEntityParam> T cast() {
        return (T) this;
    }

    public void setAoiPublic(boolean aoiPublic) {
        this.aoiPublic = aoiPublic;
    }

    public boolean isAoiPublic() {
        return aoiPublic;
    }

    public void setLeaveMapTimeStampMills(long leaveMapTimeStampMills) {
        this.leaveMapTimeStampMills = leaveMapTimeStampMills;
    }

    public long getLeaveMapTimeStampMills() {
        return leaveMapTimeStampMills;
    }

    public void setOverrideSummonCount(int overrideSummonCount) {
        this.overrideSummonCount = overrideSummonCount;
    }

    public int getOverrideSummonCount() {
        return overrideSummonCount;
    }

    public Map<String, String> toReplaceHolders() {
        return new HashMap<>();
    }

    public abstract int getSummonType();
}
